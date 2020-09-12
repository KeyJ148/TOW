package tech.abro.tow.client.tanks.equipment.bullet;

import tech.abro.orchengine.gameobject.components.Movement;
import tech.abro.orchengine.setting.ConfigReader;
import tech.abro.tow.client.tanks.player.Bullet;

public class BFury extends Bullet {

    @Override
    public void loadData(){
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        double minSpeed = cr.findDouble("MIN_SPEED");
        double maxSpeed = cr.findDouble("MAX_SPEED");
        double minDamage = cr.findDouble("MIN_DAMAGE");
        double maxDamage = cr.findDouble("MAX_DAMAGE");

        damage += maxDamage - ((player.hp/player.stats.hpMax) * (maxDamage-minDamage));
        getComponent(Movement.class).speed += maxSpeed - ((player.hp/player.stats.hpMax) * (maxSpeed-minSpeed));
    }
}

