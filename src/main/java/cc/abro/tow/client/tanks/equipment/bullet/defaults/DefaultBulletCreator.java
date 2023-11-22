package cc.abro.tow.client.tanks.equipment.bullet.defaults;

import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.StoredBulletCreator;
import cc.abro.tow.client.tanks.stats.Stats;
import cc.abro.tow.client.tanks.tank.Tank;

@StoredBulletCreator
public class DefaultBulletCreator implements BulletCreator {

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public Bullet createBullet(Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit) {
        Stats stats = tankAttacker.getTankStatsComponent().getStats();
        return new DefaultBullet(tankAttacker, x, y, direction, spriteName, soundHit, stats.getBulletExplosionPower(), stats.getRange(),
                stats.getDamage(), stats.getBulletSpeed());
    }
}
