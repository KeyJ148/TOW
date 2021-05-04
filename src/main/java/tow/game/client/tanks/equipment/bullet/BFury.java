package tow.game.client.tanks.equipment.bullet;

import tow.engine.gameobject.components.Movement;
import tow.engine.setting.ConfigReader;
import tow.game.client.tanks.player.Bullet;

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

