package cc.abro.tow.client.tanks.equipment.bullet.defaults;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.StoredBulletCreator;
import cc.abro.tow.client.tanks.tank.Tank;

@StoredBulletCreator
public class DefaultBulletCreator implements BulletCreator {

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public Bullet createBullet(Location location, Tank tankAttacker, double x, double y, double direction, String spriteName,
                               String soundHit, double speed, double range, double damage, double explosionPower) {
        return new DefaultBullet(location, tankAttacker, x, y, direction, spriteName, soundHit, speed, range, damage, explosionPower);
    }
}
