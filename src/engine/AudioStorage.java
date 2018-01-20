package engine;

import engine.io.Logger;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public class AudioStorage {

    public static final String PATH_TO_AUDIO_ROOT = "res/audio/";

    private static TreeMap<String, Audio> audios = new TreeMap<>();

    public static void init(){
        loadFromDirectory(new File(PATH_TO_AUDIO_ROOT), "wav");
    }

    private static void loadFromDirectory(File directiory, String format){
        for(File file : directiory.listFiles()){
            if (file.isDirectory()){
                loadFromDirectory(file, format);
            } else {
                String name = file.getName();
                if (name.substring(name.lastIndexOf(".")+1).equals(format)) load(file.getPath());
            }
        }
    }

    private static void load(String path){
        try{
            Audio audio = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(path));

            String name = new File(path).getName();
            String shortName = name.substring(0, name.lastIndexOf("."));
            audios.put(shortName, audio);

            Logger.println("Load audio \"" + path + "\" complited", Logger.Type.DEBUG_AUDIO);
        } catch (IOException e1) {
            Logger.println("Audio \"" + path + "\" not loading", Logger.Type.ERROR);
        }
    }

    public static Audio getAudio(String name){
        if (audios.containsKey(name)) return audios.get(name);

        Logger.println("Not find audio: " + name, Logger.Type.ERROR);
        return null;
    }

    public static void playSoundEffect(String name){
        Audio audio = getAudio(name);
        if (audio != null) audio.playAsSoundEffect(1.0f, 1.0f, false);
    }
}
