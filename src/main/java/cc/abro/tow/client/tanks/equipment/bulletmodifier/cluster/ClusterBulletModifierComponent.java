package cc.abro.tow.client.tanks.equipment.bulletmodifier.cluster;

import cc.abro.tow.client.tanks.equipment.bullet.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierComponent;
import cc.abro.tow.client.tanks.stats.Effect;

import java.util.Map;

public class ClusterBulletModifierComponent extends DefaultBulletModifierComponent {
    public record BulletInfo(BulletCreator bulletCreator, String spriteName, String soundHit) {}

    public ClusterBulletModifierComponent(String name,
                                          String title,
                                          Effect effect,
                                          int techLevel,
                                          Map<String, BulletInfo> bulletMapping) {
        super(name, title, effect, techLevel);
    }
}
