package tow.game.client.tanks.equipment.armor;

import tow.engine3.setting.ConfigReader;
import tow.game.client.tanks.player.Armor;

public class AFury extends Armor {

    public double speedUpMax;
    public double speedUpMin;

    @Override
    public void update(long delta){
        super.update(delta);

        effect.addition.speedTankUp = speedUpMax - ((player.hp/player.stats.hpMax) * (speedUpMax-speedUpMin));
        effect.addition.speedTankDown = effect.addition.speedTankUp/2;
    }

    @Override
    public void loadData(){
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        speedUpMax = cr.findDouble("MAX_SPEED_UP");
        speedUpMin = cr.findDouble("MIN_SPEED_UP");
    }
}
