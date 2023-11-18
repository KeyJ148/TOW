package cc.abro.tow.client.tanks.enemy;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.bullet.BulletComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.tank.Tank;
import lombok.Getter;

public class EnemyTank extends Tank {

    private final Collision collisionComponent;
    @Getter
    private final EnemyTankNetworkComponent enemyTankNetworkComponent;

    public EnemyTank(Location location, double x, double y, double direction,
                     ArmorComponent armorComponent, GunComponent gunComponent, BulletComponent bulletComponent,
                     int enemyId) {
        super(location, x, y, direction, armorComponent, gunComponent, bulletComponent);

        collisionComponent = new Collision(armorComponent.getAnimation().mask(), CollidableObjectType.ENEMY_TANK);
        addComponent(collisionComponent);

        enemyTankNetworkComponent = new EnemyTankNetworkComponent(enemyId);
        addComponent(enemyTankNetworkComponent);
    }

    public void setData(int x, int y, int direction, int directionGun, double speed, double moveDirection, long numberPackage) {
        enemyTankNetworkComponent.setData(x, y, direction, directionGun, speed, moveDirection, numberPackage);
    }
}
