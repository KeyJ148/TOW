package cc.abro.tow.client.tanks.equipment.bullet.accelerated;


import cc.abro.orchengine.events.UpdateEvent;
import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.tank.Tank;
import com.google.common.eventbus.Subscribe;

public class AcceleratedBullet extends Bullet {

    private final double acceleration;
    private final double speedMax;

    public AcceleratedBullet(Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit,
                             double explosionPower, double range, double damage, double speed,
                             double acceleration, double speedMax) {
        super(tankAttacker, x, y, direction, spriteName, soundHit, explosionPower, range, damage, speed);
        this.acceleration = acceleration;
        this.speedMax = speedMax;
    }

    @Subscribe
    public void onUpdateEventForAccelerate(UpdateEvent updateEvent) { //TODO не передаем изменение скорости по сети и не симулируем на клиенте
        double deltaInSeconds = ((double) updateEvent.getDelta()) / 1000000000;
        double currentSpeed = getMovementComponent().getSpeed();
        double newSpeed = Math.min(speedMax, currentSpeed + acceleration * deltaInSeconds);
        getMovementComponent().setSpeed(newSpeed);
    }

    //TODO если стоя на месте вращать пушку в одну сторону несколько секунд, то она прыгает на новое место и как будто моментально крутится
}
