package cc.abro.tow.client.tanks.equipment.armor.fury;

import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class FuryArmorComponent extends DefaultArmorComponent {

    private final double speedUpMax;
    private final double speedUpMin;
    private final double speedDownMax;
    private final double speedDownMin;

    public FuryArmorComponent(String name,
                              String title,
                              Effect effect,
                              double animationSpeedCoefficient,
                              Animation animation,
                              double speedUpMax,
                              double speedUpMin,
                              double speedDownMax,
                              double speedDownMin) {
        super(name, title, effect, animationSpeedCoefficient, animation);
        this.speedUpMax = speedUpMax;
        this.speedUpMin = speedUpMin;
        this.speedDownMax = speedDownMax;
        this.speedDownMin = speedDownMin;
    }

    //TODO implement Update and release speed change
    /*
    @Override
    public void update(long delta) {
        super.update(delta);

        effect.addition.speedTankUp = speedUpMax - ((player.hp / player.stats.hpMax) * (speedUpMax - speedUpMin));
        effect.addition.speedTankDown = effect.addition.speedTankUp / 2;
    }

    @Override
    public void loadData() {
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        speedUpMax = cr.findDouble("MAX_SPEED_UP");
        speedUpMin = cr.findDouble("MIN_SPEED_UP");
    }
     */
}
