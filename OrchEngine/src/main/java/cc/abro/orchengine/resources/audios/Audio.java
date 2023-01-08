package cc.abro.orchengine.resources.audios;

import lombok.Getter;

import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;

public class Audio {

    @Getter
    private final int id;

    public Audio() {
        id = alGenBuffers();
    }

    public void delete() {
        alDeleteBuffers(id);
    }
}
