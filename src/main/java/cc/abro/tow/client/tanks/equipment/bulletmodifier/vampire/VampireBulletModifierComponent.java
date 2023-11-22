package cc.abro.tow.client.tanks.equipment.bulletmodifier.vampire;

import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class VampireBulletModifierComponent extends DefaultBulletModifierComponent implements Updatable {
    private final double minDamage;
    private final double maxDamage;

    public VampireBulletModifierComponent(String name,
                                          String title,
                                          Effect effect,
                                          int techLevel,
                                          double minDamage,
                                          double maxDamage) {
        super(name, title, effect, techLevel);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }
    @Override
    public void update(long delta) {
        double vampire = getGameObject().getTankVampireComponent().getVampire();
        getEffect().getMulti().setDamage(maxDamage - ((1 - vampire) * (maxDamage - minDamage)));
    }
}
