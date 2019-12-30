package tow.game.client.tanks.equipment.gun;

import tow.engine3.setting.ConfigReader;
import tow.game.client.tanks.player.Gun;

public class GVampire extends Gun {

    public double attackSpeedMax;
    public double attackSpeedMin;

    @Override
    public void update(long delta){
        super.update(delta);

        effect.addition.attackSpeed = attackSpeedMax - ((1-player.vampire) * (attackSpeedMax-attackSpeedMin));
    }

    @Override
    public void loadData(){
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        attackSpeedMax = cr.findDouble("MAX_ATTACK_SPEED");
        attackSpeedMin = cr.findDouble("MIN_ATTACK_SPEED");
    }
}