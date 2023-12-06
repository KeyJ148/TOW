package cc.abro.tow.client.tanks.equipment.bullet.cluster;


import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.tank.Tank;

public class ClusterBullet extends Bullet {

    public ClusterBullet(Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit,
                         double explosionPower, double range, double damage, double speed) {
        super(tankAttacker, x, y, direction, spriteName, soundHit, explosionPower, range, damage, speed);
    }
}
