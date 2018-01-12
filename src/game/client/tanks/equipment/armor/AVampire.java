package game.client.tanks.equipment.armor;

import engine.setting.ConfigReader;
import game.client.tanks.player.Armor;

public class AVampire extends Armor{

    public double speedUpMax;
    public double speedUpMin;
    public double speedUpAdd;
    public double speedUpSub;

    @Override
    public void update(long delta){
        super.update(delta);

        effect.addition.speedTankUp -= speedUpSub * ((double) delta/1000000000);
        if (effect.addition.speedTankUp < speedUpMin) effect.addition.speedTankUp = speedUpMin;
        setSpeedDown();
    }

    @Override
    public void loadData(){
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        speedUpMax = cr.findDouble("MAX_SPEED_UP");
        speedUpMin = cr.findDouble("MIN_SPEED_UP");
        speedUpAdd = cr.findDouble("ADD_SPEED_UP");
        speedUpSub = cr.findDouble("SUB_SPEED_UP");
    }

    public void hitting() {
        effect.addition.speedTankUp += speedUpAdd;
        if (effect.addition.speedTankUp > speedUpMax) effect.addition.speedTankUp = speedUpMax;
        setSpeedDown();
    }

    public void setSpeedDown(){
        effect.addition.speedTankDown = effect.addition.speedTankUp * (6.0/10.0);
    }
}
