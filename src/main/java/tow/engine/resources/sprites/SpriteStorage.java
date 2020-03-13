package tow.engine.resources.sprites;

import tow.engine.Global;
import tow.engine.Loader;
import tow.engine.logger.Logger;
import tow.engine.resources.JsonContainerLoader;
import tow.engine.resources.audios.Audio;
import tow.engine.resources.audios.AudioLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteStorage {

    private static final String CONFIG_PATH = "res/configs/sprite.json";

    private Map<String, Sprite> spriteByName = new HashMap<>();

    public SpriteStorage(){
        try {
            SpriteContainer[] spriteContainers = JsonContainerLoader.loadExternalFile(SpriteContainer[].class, CONFIG_PATH);

            for (SpriteContainer spriteContainer : spriteContainers) {
                spriteByName.put(spriteContainer.name, SpriteLoader.getSprite(spriteContainer.texturePath, spriteContainer.maskPath));
            }
        } catch (IOException e){
            Global.logger.println("Error loading sprites", e, Logger.Type.ERROR);
            Loader.exit();
        }
    }

    public Sprite getSprite(String name){
        if (!spriteByName.containsKey(name)){
            Global.logger.print("Sprite \"" + name + "\" not found", Logger.Type.ERROR);
            return null;
        }

        return spriteByName.get(name);
    }

    private static class SpriteContainer{
        public String name;
        public String texturePath;
        public String maskPath;
    }
}
