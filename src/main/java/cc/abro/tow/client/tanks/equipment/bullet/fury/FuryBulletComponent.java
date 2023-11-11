package cc.abro.tow.client.tanks.equipment.bullet.fury;

import cc.abro.tow.client.tanks.equipment.bullet.BulletComponent;
import cc.abro.tow.client.tanks.equipment.bullet.defaults.DefaultBulletComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class FuryBulletComponent extends DefaultBulletComponent {
    private final double minSpeed;
    private final double maxSpeed;
    private final double minDamage;
    private final double maxDamage;

    public FuryBulletComponent(String name,
                               String title,
                               Effect effect,
                               int techLevel,
                               double minSpeed,
                               double maxSpeed,
                               double minDamage,
                               double maxDamage) {
        super(name, title, effect, techLevel);
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }
}
