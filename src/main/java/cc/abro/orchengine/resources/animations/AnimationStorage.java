package cc.abro.orchengine.resources.animations;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.exceptions.EngineException;
import cc.abro.orchengine.resources.JsonContainerLoader;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@EngineService
public class AnimationStorage {

    private static final String CONFIG_PATH = "configs/animation.json";

    private Map<String, Animation> animationByName = new HashMap<>();

    public AnimationStorage() {
        try {
            AnimationContainer[] animationContainers = JsonContainerLoader.loadInternalFile(AnimationContainer[].class, CONFIG_PATH);

            for (AnimationContainer animationContainer : animationContainers) {
                if (animationByName.containsKey(animationContainer.name)) {
                    log.error("Animation \"" + animationContainer.name + "\" already exists");
                    throw new IllegalStateException("Animation \"" + animationContainer.name + "\" already exists");
                }

                animationByName.put(animationContainer.name, AnimationLoader.getAnimation(animationContainer.texturePaths, animationContainer.maskPath));
            }
        } catch (IOException e) {
            log.error("Error loading animation", e);
            throw new EngineException(e);
        }
    }

    public Animation getAnimation(String name) {
        if (!animationByName.containsKey(name)) {
            log.error("Animation \"" + name + "\" not found");
            return null;
        }

        return animationByName.get(name);
    }

    private static record AnimationContainer(String name, String[] texturePaths, String maskPath) {
    }

}
