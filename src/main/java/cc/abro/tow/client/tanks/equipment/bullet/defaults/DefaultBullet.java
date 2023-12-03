package cc.abro.tow.client.tanks.equipment.bullet.defaults;


import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.tank.Tank;

public class DefaultBullet extends Bullet {

    public DefaultBullet(Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit) {
        super(tankAttacker, x, y, direction, spriteName, soundHit);
    }
}
