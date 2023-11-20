package cc.abro.tow.client.tanks.equipment.bullet1;

import cc.abro.orchengine.gameobject.Location;

public interface BulletCreator {

    String getName();
    Bullet createBullet(Location location, double x, double y, double direction, String spriteName,
                        String soundHit, double speed, double range, double damage, double explosionPower);
}
