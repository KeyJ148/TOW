package cc.abro.orchengine.resources.sprites;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Loader;
import cc.abro.orchengine.logger.Logger;
import cc.abro.orchengine.resources.JsonContainerLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteStorage {

    private static final String CONFIG_PATH = "res/configs/sprite.json";

    private Map<String, Sprite> spriteByName = new HashMap<>();

    public SpriteStorage() {
        try {
            SpriteContainer[] spriteContainers = JsonContainerLoader.loadInternalFile(SpriteContainer[].class, CONFIG_PATH);

            for (SpriteContainer spriteContainer : spriteContainers) {
                if (spriteByName.containsKey(spriteContainer.name)) {
                    Global.logger.println("Sprite \"" + spriteContainer.name + "\" already exists", Logger.Type.ERROR);
                    Loader.exit();
                }

                spriteByName.put(spriteContainer.name, SpriteLoader.getSprite(spriteContainer.texturePath, spriteContainer.maskPath));
            }
        } catch (IOException e) {
            Global.logger.println("Error loading sprites", e, Logger.Type.ERROR);
            Loader.exit();
        }
    }

    public Sprite getSprite(String name) {
        if (!spriteByName.containsKey(name)) {
            Global.logger.print("Sprite \"" + name + "\" not found", Logger.Type.ERROR);
            return null;
        }

        return spriteByName.get(name);
    }

    private static class SpriteContainer {
        public String name;
        public String texturePath;
        public String maskPath;
    }
}
