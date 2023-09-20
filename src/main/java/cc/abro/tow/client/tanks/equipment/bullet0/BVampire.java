package cc.abro.tow.client.tanks.equipment.bullet0;

import cc.abro.tow.client.ConfigReader;

public class BVampire extends Bullet {

    @Override
    public void loadData() {
        super.loadData();

        ConfigReader cr = new ConfigReader(PATH_SETTING + "BVampire" + ".properties");
        double maxDamage = cr.findDouble("MAX_DAMAGE");
        double minDamage = cr.findDouble("MIN_DAMAGE");

        //damage += maxDamage - ((1 - player.vampire) * (maxDamage - minDamage));
    }

}

