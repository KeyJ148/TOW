package game.client.tanks.equipment.armor;

import engine.setting.ConfigReader;
import game.client.tanks.player.Armor;

public class AVampire extends Armor{

    public double speedUpMax;
    public double speedUpMin;
    public double directionTankUpMin;
    public double directionTankUpMax;

    @Override
    public void update(long delta){
        super.update(delta);

        effect.addition.speedTankUp = speedUpMax - ((1-player.vampire) * (speedUpMax-speedUpMin));
        effect.addition.directionTankUp = directionTankUpMax - ((1-player.vampire) * (directionTankUpMax-directionTankUpMin));
        setSpeedDown();
    }

    @Override
    public void loadData(){
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        speedUpMax = cr.findDouble("MAX_SPEED_UP");
        speedUpMin = cr.findDouble("MIN_SPEED_UP");
        directionTankUpMax = cr.findDouble("MAX_DIRECTION_TANK_UP");
        directionTankUpMin = cr.findDouble("MIN_DIRECTION_TANK_UP");
    }

    public void setSpeedDown(){
        effect.addition.speedTankDown = effect.addition.speedTankUp * (6.0/10.0);
    }
}
