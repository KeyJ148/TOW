package cc.abro.tow.client.tanks.equipment.bullet.mounted;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.StoredBulletCreator;
import cc.abro.tow.client.tanks.stats.Stats;
import cc.abro.tow.client.tanks.tank.Tank;

@StoredBulletCreator
public class MountedBulletCreator implements BulletCreator {

    @Override
    public String getName() {
        return "mounted";
    }

    @Override
    public Bullet createBullet(Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit) {
        Stats stats = tankAttacker.getTankStatsComponent().getStats();
        return new MountedBullet(tankAttacker, x, y, direction, spriteName, soundHit, stats.getBulletExplosionPower(),
                Math.min(stats.getRange(), getDistanceToCursor(tankAttacker.getLocation(), x, y)), stats.getDamage(), stats.getBulletSpeed());
    }

    private double getDistanceToCursor(Location location, double startX, double startY) {
        Vector2<Integer> relativeCursorPosition = location.getGuiLocationFrame().getMouse().getCursor().getPosition();
        Vector2<Double> absoluteCursorPosition = location.getCamera().toAbsolutePosition(
                new Vector2<>((double) relativeCursorPosition.x, (double) relativeCursorPosition.y));
        return Math.sqrt(Math.pow(absoluteCursorPosition.x - startX, 2) + Math.pow(absoluteCursorPosition.y - startY, 2));
    }
}
