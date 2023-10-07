package cc.abro.tow.client.tanks.equipment.armor.vampire;

import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class VampireArmorComponent extends DefaultArmorComponent implements Updatable {

    private final double speedUpMax;
    private final double speedUpMin;
    private final double speedDownMax;
    private final double speedDownMin;
    private final double speedRotateTankMin;
    private final double speedRotateTankMax;

    public VampireArmorComponent(String name,
                                 String title,
                                 Effect effect,
                                 double animationSpeedCoefficient,
                                 Animation animation,
                                 double speedUpMax,
                                 double speedUpMin,
                                 double speedDownMax,
                                 double speedDownMin,
                                 double speedRotateTankMin,
                                 double speedRotateTankMax
    ) {
        super(name, title, effect, animationSpeedCoefficient, animation);
        this.speedUpMax = speedUpMax;
        this.speedUpMin = speedUpMin;
        this.speedDownMax = speedDownMax;
        this.speedDownMin = speedDownMin;
        this.speedRotateTankMin = speedRotateTankMin;
        this.speedRotateTankMax = speedRotateTankMax;
    }

    @Override
    public void update(long delta) {
        double vampire = getGameObject().getTankVampireComponent().getVampire();
        double newSpeedUp = speedUpMax - ((1 - vampire) * (speedUpMax - speedUpMin));
        double newSpeedDown = speedDownMax - ((1 - vampire) * (speedDownMax - speedDownMin));
        double newSpeedRotateTank = speedRotateTankMax - ((1 - vampire) * (speedRotateTankMax - speedRotateTankMin));

        getEffect().getAddition().setSpeedUp(newSpeedUp);
        getEffect().getAddition().setSpeedDown(newSpeedDown);
        getEffect().getAddition().setSpeedRotateTank(newSpeedRotateTank);
    }
}
