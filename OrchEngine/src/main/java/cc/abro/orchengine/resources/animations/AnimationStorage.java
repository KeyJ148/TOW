package cc.abro.orchengine.resources.animations;

import cc.abro.orchengine.context.EngineService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@EngineService
public class AnimationStorage {

    private final AnimationLoader animationLoader;
    private final Map<String, Animation> animationByName = new HashMap<>();

    public AnimationStorage(AnimationLoader animationLoader) {
        this.animationLoader = animationLoader;
    }

    public void loadAnimations(List<AnimationContainer> animationContainers) {
        for (AnimationContainer animationContainer : animationContainers) {
            if (animationByName.containsKey(animationContainer.name)) {
                log.error("Animation \"" + animationContainer.name + "\" already exists");
                throw new IllegalStateException("Animation \"" + animationContainer.name + "\" already exists");
            }

            animationByName.put(animationContainer.name, animationLoader.getAnimation(animationContainer.texturePaths, animationContainer.maskPath));
        }
    }

    public Animation getAnimation(String name) {
        if (!animationByName.containsKey(name)) {
            log.error("Animation \"" + name + "\" not found");
            return null;
        }

        return animationByName.get(name);
    }

    public record AnimationContainer(String name, String[] texturePaths, String maskPath) {
    }

}
