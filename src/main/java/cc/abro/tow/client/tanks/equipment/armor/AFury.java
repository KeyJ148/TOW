package cc.abro.tow.client.tanks.equipment.armor;

import cc.abro.orchengine.setting.ConfigReader;
import cc.abro.tow.client.tanks.player.Armor;

public class AFury extends Armor {

    public double speedUpMax;
    public double speedUpMin;

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
}
