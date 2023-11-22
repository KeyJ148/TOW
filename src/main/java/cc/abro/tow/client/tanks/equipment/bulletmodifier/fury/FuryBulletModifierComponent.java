package cc.abro.tow.client.tanks.equipment.bulletmodifier.fury;

import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierComponent;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;

public class FuryBulletModifierComponent extends DefaultBulletModifierComponent implements Updatable {
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
    @Override
    public void update(long delta) {
        Stats stats = getGameObject().getTankStatsComponent().getStats();
        double percentHp = getGameObject().getTankStatsComponent().getCurrentHp() /
                stats.getHpMax();
        getEffect().getMulti().setBulletSpeed(maxSpeed - percentHp * (maxSpeed - minSpeed));
        getEffect().getMulti().setDamage(maxDamage - percentHp * (maxDamage - minDamage));
    }
}
