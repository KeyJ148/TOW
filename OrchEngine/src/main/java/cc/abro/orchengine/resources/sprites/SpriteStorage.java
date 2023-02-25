package cc.abro.orchengine.resources.sprites;

import cc.abro.orchengine.context.EngineService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@EngineService
public class SpriteStorage {

    private final SpriteLoader spriteLoader;
    private final Map<String, Sprite> spriteByName = new HashMap<>();

    public SpriteStorage(SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
    }

    public void loadSprites(List<SpriteContainer> spriteContainers) {
        for (SpriteContainer spriteContainer : spriteContainers) {
            if (spriteByName.containsKey(spriteContainer.name)) {
                log.error("Sprite \"" + spriteContainer.name + "\" already exists");
                throw new IllegalStateException("Sprite \"" + spriteContainer.name + "\" already exists");
            }

            spriteByName.put(spriteContainer.name, spriteLoader.getSprite(spriteContainer.texturePath, spriteContainer.maskPath));
        }
    }

    public Sprite getSprite(String name) {
        if (!spriteByName.containsKey(name)) {
            log.error("Sprite \"" + name + "\" not found");
            return null;
        }

        return spriteByName.get(name);
    }

    public record SpriteContainer(String name, String texturePath, String maskPath) {
    }
}
