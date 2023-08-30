package cc.abro.tow.client.tanks.equipment.armor.vampire;

import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class VampireArmorComponent extends DefaultArmorComponent {

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

    //TODO implement Update and release speed change
    /*
    @Override
    public void update(long delta) {
        getEffect().addition.speedTankUp = speedUpMax - ((1 - player.vampire) * (speedUpMax - speedUpMin));
        getEffect()f.addition.directionTankUp = directionTankUpMax - ((1 - player.vampire) * (directionTankUpMax - directionTankUpMin));
        setSpeedDown();


        getGameObject().getTankStatsComponent().updateStats(); //TODO сделать так же и в других Vampire/Fury компонентах
    }

    @Override //TODO factory collection? As a MapObjectFactory
    public void loadData() {
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        speedUpMax = cr.findDouble("MAX_SPEED_UP");
        speedUpMin = cr.findDouble("MIN_SPEED_UP");
        directionTankUpMax = cr.findDouble("MAX_DIRECTION_TANK_UP");
        directionTankUpMin = cr.findDouble("MIN_DIRECTION_TANK_UP");
    }

    public void setSpeedDown() {
        effect.addition.speedTankDown = effect.addition.speedTankUp * (6.0 / 10.0);
    }
     */
}
