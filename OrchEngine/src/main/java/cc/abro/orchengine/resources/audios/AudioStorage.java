package cc.abro.orchengine.resources.audios;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.exceptions.EngineException;
import cc.abro.orchengine.resources.JsonContainerLoader;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@EngineService
public class AudioStorage {

    private static final String CONFIG_PATH = "configs/resources/audio.json";

    private final Map<String, Audio> audioByName = new HashMap<>();

    public AudioStorage(AudioLoader audioLoader) {
        try {
            Map<String, String> audioNameToPath = JsonContainerLoader.loadInternalFile(Map.class, CONFIG_PATH);

            for (Map.Entry<String, String> entry : audioNameToPath.entrySet()) {
                String name = entry.getKey();
                String path = entry.getValue();
                if (audioByName.containsKey(name)) {
                    log.error("Audio \"" + name + "\" already exists");
                    throw new IllegalStateException("Audio \"" + name + "\" already exists");
                }

                audioByName.put(name, audioLoader.getAudio(path));
            }
        } catch (IOException e) {
            log.error("Error loading audios", e);
            throw new EngineException(e);
        }
    }

    public Audio getAudio(String name) {
        if (!audioByName.containsKey(name)) {
            log.error("Audio \"" + name + "\" not found");
            return null;
        }

        return audioByName.get(name);
    }
}
