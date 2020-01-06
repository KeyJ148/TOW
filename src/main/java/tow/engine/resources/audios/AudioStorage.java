package tow.engine.resources.audios;

import tow.engine.Global;
import tow.engine.logger.Logger;
import tow.engine.resources.JsonContainerLoader;
import tow.engine2.Loader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioStorage {

    private static final String CONFIG_PATH = "res/configs/audio.json";

    private Map<String, Audio> audioByName = new HashMap<>();

    public AudioStorage(){
        try {
            Map<String, String> audioNameToPath = JsonContainerLoader.loadInternalFile(Map.class, CONFIG_PATH);

            for (Map.Entry<String, String> entry : audioNameToPath.entrySet()) {
                String name = entry.getKey();
                String path = entry.getValue();
                audioByName.put(name, AudioLoader.getAudio(path));
            }
        } catch (IOException e){
            Global.logger.println("Error loading audios", e, Logger.Type.ERROR);
            Loader.exit();
        }
    }

    public Audio getAudio(String name){
        if (!audioByName.containsKey(name)){
            Global.logger.print("Audio \"" + name + "\" not found", Logger.Type.ERROR);
            return null;
        }

        return audioByName.get(name);
    }
}
