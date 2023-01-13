package cc.abro.orchengine.resources.audios;

import lombok.Getter;

import static org.lwjgl.openal.AL10.*;

public class AudioSource {

    @Getter
    private final int id;

    public AudioSource() {
        id = alGenSources();
    }

    public void setAudio(Audio audio) {
        alSourcei(id, AL_BUFFER, audio.getId());
    }

    //Установка громкости в диапазоне [0; 1]
    public void setVolume(float volume) {
        alSourcef(id, AL_GAIN, volume);
    }

    public void play() {
        alSourcePlay(id);
    }

    public void pause() {
        alSourcePause(id);
    }

    public void stop() {
        alSourceStop(id);
    }

    public boolean isPlaying() {
        return getState() == AL_PLAYING;
    }

    public boolean isPaused() {
        return getState() == AL_PAUSED;
    }

    public boolean isStopped() {
        return getState() == AL_STOPPED;
    }

    public void delete() {
        stop();
        alDeleteSources(id);
    }

    private int getState() {
        return alGetSourcei(id, AL_SOURCE_STATE);
    }

}
