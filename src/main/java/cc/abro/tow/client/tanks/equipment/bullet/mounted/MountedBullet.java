package cc.abro.tow.client.tanks.equipment.bullet.mounted;


import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.components.collision.CollidableComponent;
import cc.abro.orchengine.gameobject.components.collision.CollisionType;
import cc.abro.orchengine.gameobject.components.collision.DefaultCollidableObjectType;
import cc.abro.orchengine.input.mouse.MouseHandler;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.DepthConstants;
import cc.abro.tow.client.tanks.enemy.EnemyTank;
import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.tank.Tank;

public class MountedBullet extends Bullet {

    private final double EXPLOSION_RANGE_COEFFICIENT = 5;

    public MountedBullet(Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit,
                         double explosionPower, double range, double damage, double speed) {
        super(tankAttacker, x, y, direction, spriteName, soundHit, explosionPower, getDistanceToCursor(x, y), damage, speed);
        getSpriteComponent().setZ(DepthConstants.MORTAR_SPRITE_Z);
    }

    @Override
    protected void destroyByRange() {
        destroyWithExplosion();
        for (EnemyTank enemyTank : Context.getService(ClientData.class).enemy.values()) {
            if (isTankInExplosionDistance(enemyTank)) {
                Context.getService(TCPControl.class).send(14, getDamage() + " " + enemyTank.getEnemyTankNetworkComponent().getId()); //TODO (отправлять и получать по шине ивентов)
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

    private boolean isTankInExplosionDistance(Tank tank) {
        return Math.sqrt(Math.pow(tank.getX() - getX(), 2) + Math.pow(tank.getY() - getY(), 2)) <=
                getExplosionPower() * EXPLOSION_RANGE_COEFFICIENT;
    }

    private static double getDistanceToCursor(double startX, double startY) {
        Vector2<Integer> cursorPos = Context.getService(MouseHandler.class).getCursor().getPosition();
        return Math.sqrt(Math.pow(cursorPos.x - startX, 2) + Math.pow(cursorPos.y - startY, 2));
    }
}
