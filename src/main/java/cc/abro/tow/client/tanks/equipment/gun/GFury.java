package cc.abro.tow.client.tanks.equipment.gun;

import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.tanks.player.Gun;

public class GFury extends Gun {

    public double attackSpeedMax;
    public double attackSpeedMin;

    @Override
    public void update(long delta) {
        super.update(delta);

        effect.addition.attackSpeed = attackSpeedMax - ((player.hp / player.stats.hpMax) * (attackSpeedMax - attackSpeedMin));
    }

    @Override
    public void loadData() {
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        attackSpeedMax = cr.findDouble("MAX_ATTACK_SPEED");
        attackSpeedMin = cr.findDouble("MIN_ATTACK_SPEED");
    }
}