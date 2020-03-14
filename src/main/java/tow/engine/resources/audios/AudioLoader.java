package tow.engine.resources.audios;

import org.lwjgl.BufferUtils;
import tow.engine.Global;
import tow.engine.logger.Logger;
import tow.engine.resources.ResourceLoader;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class AudioLoader {

    public static Audio getAudio(String path){
        try (InputStream in = ResourceLoader.getResourceAsStream(path)){
            IntBuffer channelsBuffer = BufferUtils.createIntBuffer(1);
            IntBuffer sampleRateBuffer = BufferUtils.createIntBuffer(1);

            byte[] audioFileByteArray = in.readAllBytes();
            ByteBuffer audioFileByteBuffer = BufferUtils.createByteBuffer(audioFileByteArray.length);
            audioFileByteBuffer.put(audioFileByteArray);
            audioFileByteBuffer.flip();

            ShortBuffer audioFileRawBuffer = stb_vorbis_decode_memory(audioFileByteBuffer, channelsBuffer, sampleRateBuffer);

            int channels = channelsBuffer.get();
            int sampleRate = sampleRateBuffer.get();

            int format;
            switch (channels){
                case 1: format = AL_FORMAT_MONO16; break;
                case 2: format = AL_FORMAT_STEREO16; break;
                default: format = -1;
            }

            Audio audio = new Audio();
            alBufferData(audio.getID(), format, audioFileRawBuffer, sampleRate);
            free(audioFileRawBuffer);

            Global.logger.println("Load audio \"" + path + "\" completed", Logger.Type.DEBUG_AUDIO);
            return audio;
        } catch (Exception e){
            Global.logger.println("Audio \"" + path + "\" not loading", Logger.Type.ERROR);
            return null;
        }
    }

}
