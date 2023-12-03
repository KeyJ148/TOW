package cc.abro.tow.client.tanks.equipment.bullet;

import cc.abro.tow.client.tanks.tank.Tank;

public interface BulletCreator {

    String getName();
    Bullet createBullet(Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit);
}
