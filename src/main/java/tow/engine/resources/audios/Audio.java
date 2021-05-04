package tow.engine.resources.audios;

import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;

public class Audio {

    private final int id;

    public Audio() {
        id = alGenBuffers();
    }

    public int getID(){
        return id;
    }

    public void delete(){
        alDeleteBuffers(id);
    }
}
