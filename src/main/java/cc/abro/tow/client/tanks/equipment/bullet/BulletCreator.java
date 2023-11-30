package cc.abro.tow.client.tanks.equipment.bullet;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.tanks.tank.Tank;

public interface BulletCreator {

    String getName();
    Bullet createBullet(Location location, Tank tankAttacker, double x, double y, double direction, String spriteName,
                        String soundHit, double speed, double range, double damage, double explosionPower);
}
