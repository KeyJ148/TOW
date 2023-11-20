package cc.abro.tow.client.tanks.equipment.bullet1.defaults;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.tanks.equipment.bullet1.Bullet;
import cc.abro.tow.client.tanks.equipment.bullet1.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet1.StoredBulletCreator;

@StoredBulletCreator
public class DefaultBulletCreator implements BulletCreator {

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public Bullet createBullet(Location location, double x, double y, double direction, String spriteName,
                               String soundHit, double speed, double range, double damage, double explosionPower) {
        return new DefaultBullet(location, x, y, direction, spriteName, soundHit, speed, range, damage, explosionPower);
    }
}
