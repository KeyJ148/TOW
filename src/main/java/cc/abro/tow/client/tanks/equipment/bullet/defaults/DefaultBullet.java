package cc.abro.tow.client.tanks.equipment.bullet.defaults;


import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.tanks.equipment.bullet.Bullet;

public class DefaultBullet extends Bullet {

    public DefaultBullet(Location location, double x, double y, double direction, String spriteName, String soundHit,
                         double speed, double range, double damage, double explosionPower) {
        super(location, x, y, direction, spriteName, soundHit, speed, range, damage, explosionPower);
    }
}
