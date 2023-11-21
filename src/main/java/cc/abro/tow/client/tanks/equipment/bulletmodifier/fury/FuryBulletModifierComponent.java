package cc.abro.tow.client.tanks.equipment.bulletmodifier.fury;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class FuryBulletModifierComponent extends DefaultBulletModifierComponent {
    private final double minSpeed;
    private final double maxSpeed;
    private final double minDamage;
    private final double maxDamage;

    public FuryBulletModifierComponent(String name,
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
