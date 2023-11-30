package cc.abro.tow.client.tanks.equipment.bullet.accelerated;


import cc.abro.orchengine.events.UpdateEvent;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.tank.Tank;
import com.google.common.eventbus.Subscribe;

public class AcceleratedBullet extends Bullet {

    private final double acceleration;
    private final double speedMax;

    public AcceleratedBullet(Location location, Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit,
                             double speed, double range, double damage, double explosionPower, double acceleration, double speedMax) {
        super(location, tankAttacker, x, y, direction, spriteName, soundHit, speed, range, damage, explosionPower);
        this.acceleration = acceleration;
        this.speedMax = speedMax;
    }

    @Subscribe
    public void onUpdateEventForAccelerate(UpdateEvent updateEvent) {
        double deltaInSeconds = ((double) updateEvent.getDelta()) / 1000000000;
        double currentSpeed = getMovementComponent().getSpeed();
        double newSpeed = Math.min(speedMax, currentSpeed + acceleration * deltaInSeconds);
        getMovementComponent().setSpeed(newSpeed);
    }
}
