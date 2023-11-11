package cc.abro.tow.client.tanks.equipment.bullet.vampire;

import cc.abro.tow.client.tanks.equipment.bullet.defaults.DefaultBulletComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class VampireBulletComponent extends DefaultBulletComponent {
    private final double minDamage;
    private final double maxDamage;

    public VampireBulletComponent(String name,
                                  String title,
                                  Effect effect,
                                  int techLevel,
                                  double minDamage,
                                  double maxDamage) {
        super(name, title, effect, techLevel);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }
}
