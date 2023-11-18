package cc.abro.tow.client.tanks.equipment.bulletmodifier.rocket;

import cc.abro.tow.client.tanks.equipment.bullet.accelerated.AcceleratedBulletModifier;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierComponent;
import cc.abro.tow.client.tanks.stats.Effect;
import lombok.Getter;

public class RocketBulletModifierComponent extends DefaultBulletModifierComponent implements AcceleratedBulletModifier {
    @Getter
    private final double acceleration;

    @Getter
    private final double speedMax;

    public RocketBulletModifierComponent(String name,
                                         String title,
                                         Effect effect,
                                         int techLevel,
                                         double minSpeed,
                                         double maxSpeed,
                                         double acceleration,
                                         double speedMax) {
        super(name, title, effect, techLevel);
        this.acceleration = minSpeed;
        this.speedMax = maxSpeed;
    }
}
