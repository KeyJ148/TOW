package cc.abro.tow.client.tanks.equipment.bullet.accelerated;

import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.StoredBulletCreator;
import cc.abro.tow.client.tanks.tank.Tank;

@StoredBulletCreator
public class AcceleratedBulletCreator implements BulletCreator {

    private final double MAX_SPEED_COEFFICIENT = 3.0;
    private final double ACCELERATION_COEFFICIENT = 1.0;

    @Override
    public String getName() {
        return "accelerated";
    }

    @Override
    public Bullet createBullet(Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit) {
        double speed = tankAttacker.getTankStatsComponent().getStats().getBulletSpeed();
        return new AcceleratedBullet(tankAttacker, x, y, direction, spriteName, soundHit,
                speed * ACCELERATION_COEFFICIENT, speed * MAX_SPEED_COEFFICIENT);
    }
}
