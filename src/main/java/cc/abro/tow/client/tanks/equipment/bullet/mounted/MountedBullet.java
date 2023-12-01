package cc.abro.tow.client.tanks.equipment.bullet.mounted;


import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.collision.CollidableComponent;
import cc.abro.orchengine.gameobject.components.collision.CollisionType;
import cc.abro.orchengine.gameobject.components.collision.DefaultCollidableObjectType;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.tanks.enemy.EnemyTank;
import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.tank.Tank;

public class MountedBullet extends Bullet {

    private final double EXPLOSION_RANGE_COEFFICIENT = 5;

    public MountedBullet(Location location, Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit,
                         double speed, double range, double damage, double explosionPower) {
        super(location, tankAttacker, x, y, direction, spriteName, soundHit, speed, range, damage, explosionPower);
    }

    @Override
    protected void destroyByRange() {
        destroyWithExplosion();
        for (EnemyTank enemyTank : Context.getService(ClientData.class).enemy.values()) {
            if (inExplosionDistance(enemyTank)) {
                //TODO Context.getService(TCPControl.class).send(14, damage + " " + ea.enemy.id); (получать по шине ивентов)
            }
        }
    }

    @Override
    protected void collision(CollidableComponent collision, CollisionType collisionType) {
        if (isDestroyed()) return;
        if (collisionType == CollisionType.LEAVING) return;

        if (collision.getType().equals(DefaultCollidableObjectType.BORDER)) {
            destroyWithoutExplosion();
        }
    }

    private boolean inExplosionDistance(Tank tank) {
        return Math.sqrt(Math.pow(tank.getX() - getX(), 2) + Math.pow(tank.getY() - getY(), 2)) <=
                getExplosionPower() * EXPLOSION_RANGE_COEFFICIENT;
    }
}
