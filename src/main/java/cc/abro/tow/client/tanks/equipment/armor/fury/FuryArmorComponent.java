package cc.abro.tow.client.tanks.equipment.armor.fury;

import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class FuryArmorComponent extends DefaultArmorComponent implements Updatable {

    private final double speedUpMax;
    private final double speedUpMin;
    private final double speedDownMax;
    private final double speedDownMin;

    public FuryArmorComponent(String name,
                              String title,
                              Effect effect,
                              double animationSpeedCoefficient,
                              Animation animation,
                              int size,
                              int techLevel,
                              double speedUpMax,
                              double speedUpMin,
                              double speedDownMax,
                              double speedDownMin) {
        super(name, title, effect, animationSpeedCoefficient, animation, size, techLevel);
        this.speedUpMax = speedUpMax;
        this.speedUpMin = speedUpMin;
        this.speedDownMax = speedDownMax;
        this.speedDownMin = speedDownMin;
    }

    @Override
    public void update(long delta) {
        double percentHp = getGameObject().getTankStatsComponent().getCurrentHp() /
                getGameObject().getTankStatsComponent().getStats().getHpMax();
        getEffect().getAddition().setSpeedUp(speedUpMax - (percentHp * (speedUpMax - speedUpMin)));
        getEffect().getAddition().setSpeedDown(speedDownMax - (percentHp * (speedDownMax - speedDownMin)));
    }
}
