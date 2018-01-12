package game.client.tanks.equipment.gun;

import engine.setting.ConfigReader;
import game.client.tanks.player.Gun;

public class GVampire extends Gun {

    public double attackSpeedMax;
    public double attackSpeedMin;
    public double attackSpeedAdd;
    public double attackSpeedSub;

    @Override
    public void update(long delta){
        super.update(delta);

        effect.addition.attackSpeed -= attackSpeedSub * ((double) delta/1000000000);
        if (effect.addition.attackSpeed < attackSpeedMin) effect.addition.attackSpeed = attackSpeedMin;
    }

    @Override
    public void loadData(){
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        attackSpeedMax = cr.findDouble("MAX_ATTACK_SPEED");
        attackSpeedMin = cr.findDouble("MIN_ATTACK_SPEED");
        attackSpeedAdd = cr.findDouble("ADD_ATTACK_SPEED");
        attackSpeedSub = cr.findDouble("SUB_ATTACK_SPEED");
    }

    public void hitting() {
        effect.addition.attackSpeed += attackSpeedAdd;
        if (effect.addition.attackSpeed > attackSpeedMax) effect.addition.attackSpeed = attackSpeedMax;
    }
}