package tow.engine.resources.animations;

import tow.engine.Global;
import tow.engine.Loader;
import tow.engine.logger.Logger;
import tow.engine.resources.JsonContainerLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnimationStorage {

    private static final String CONFIG_PATH = "res/configs/animation.json";

    private Map<String, Animation> animationByName = new HashMap<>();

    public AnimationStorage(){
        try {
            AnimationContainer[] animationContainers = JsonContainerLoader.loadInternalFile(AnimationContainer[].class, CONFIG_PATH);

            for (AnimationContainer animationContainer : animationContainers) {
                if (animationByName.containsKey(animationContainer.name)){
                    Global.logger.println("Animation \"" + animationContainer.name + "\" already exists", Logger.Type.ERROR);
                    Loader.exit();
                }

                animationByName.put(animationContainer.name, AnimationLoader.getAnimation(animationContainer.texturePaths, animationContainer.maskPath));
            }
        } catch (IOException e){
            Global.logger.println("Error loading animation", e, Logger.Type.ERROR);
            Loader.exit();
        }
    }

    public Animation getAnimation(String name){
        if (!animationByName.containsKey(name)){
            Global.logger.print("Animation \"" + name + "\" not found", Logger.Type.ERROR);
            return null;
        }

        return animationByName.get(name);
    }

    private static class AnimationContainer{
        String name;
        String[] texturePaths;
        String maskPath;
    }
}
