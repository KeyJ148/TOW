package cc.abro.tow.client.tanks.equipment.bullet;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.resources.JsonContainerLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@GameService
@RequiredArgsConstructor
public class BulletSpriteStorage {

    private final String BULLET_SPRITE_CONFIG_PATH = "configs/game/bullet_sprite.json";

    private final Map<String, BulletSpriteSpecification> bulletSpriteByName = new HashMap<>();

    public void init() {
        try {
            BulletSpriteSpecification[] bulletSpriteSpecifications = JsonContainerLoader.loadInternalFile(
                    BulletSpriteSpecification[].class, BULLET_SPRITE_CONFIG_PATH);
            for (BulletSpriteSpecification bulletSpriteSpecification : bulletSpriteSpecifications) {
                addBulletSpriteSpecification(bulletSpriteSpecification);
            }
        } catch (IOException e) {
            log.fatal("Error loading BulletSpriteInfo", e);
            throw new RuntimeException(e);
        }
    }

    public void addBulletSpriteSpecification(BulletSpriteSpecification bulletSpriteSpecification) {
        if (bulletSpriteByName.containsKey(bulletSpriteSpecification.spriteName)) {
            log.error("BulletSpriteInfo \"" + bulletSpriteSpecification.spriteName + "\" already exists");
            throw new IllegalStateException("BulletSpriteInfo \"" +
                    bulletSpriteSpecification.spriteName + "\" already exists");
        }

        bulletSpriteByName.put(bulletSpriteSpecification.spriteName, bulletSpriteSpecification);
    }

    public BulletSpriteSpecification getBulletSpriteSpecification(String name) {
        if (!bulletSpriteByName.containsKey(name)) {
            log.error("BulletSpriteInfo \"" + name + "\" not found");
            return null;
        }

        return bulletSpriteByName.get(name);
    }

    public record BulletSpriteSpecification(String spriteName, boolean rotation) {}

}
