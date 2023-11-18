package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.gameobject.components.collision.DefaultCollidableObjectType;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.bullet.BulletComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.tank.Tank;
import lombok.Getter;

public class PlayerTank extends Tank {

    private final Collision collisionComponent;
    private final PlayerTankNetworkComponent playerTankNetworkComponent;
    private final PlayerTankMovementControllerComponent playerTankMovementControllerComponent;
    private final PlayerTankGunAttackControllerComponent playerTankGunAttackControllerComponent;
    private final PlayerTankGunRotateControllerComponent playerTankGunRotateControllerComponent;
    @Getter
    private final PlayerTankEquipmentControllerComponent playerTankEquipmentControllerComponent;
    @Getter
    private final PlayerTankEquipmentGuiComponent playerTankEquipmentGuiComponent;
    private final PlayerTankHpGuiComponent playerTankHpGuiComponent;
    private final PlayerTankStatsGuiComponent playerTankStatsGuiComponent;
    private final PlayerTankSingleplayerControllerComponent playerTankSingleplayerControllerComponent;

    public PlayerTank(Location location, double x, double y, double direction,
                      ArmorComponent armorComponent, GunComponent gunComponent, BulletComponent bulletComponent) {
        super(location, x, y, direction, armorComponent, gunComponent, bulletComponent);

        playerTankNetworkComponent = new PlayerTankNetworkComponent();
        addComponent(playerTankNetworkComponent);

        playerTankMovementControllerComponent = new PlayerTankMovementControllerComponent();
        addComponent(playerTankMovementControllerComponent);

        playerTankGunAttackControllerComponent = new PlayerTankGunAttackControllerComponent();
        addComponent(playerTankGunAttackControllerComponent);

        playerTankGunRotateControllerComponent = new PlayerTankGunRotateControllerComponent();
        addComponent(playerTankGunRotateControllerComponent);

        playerTankEquipmentControllerComponent = new PlayerTankEquipmentControllerComponent();
        addComponent(playerTankEquipmentControllerComponent);

        playerTankEquipmentGuiComponent = new PlayerTankEquipmentGuiComponent();
        addComponent(playerTankEquipmentGuiComponent);

        playerTankHpGuiComponent = new PlayerTankHpGuiComponent();
        addComponent(playerTankHpGuiComponent);

        playerTankStatsGuiComponent = new PlayerTankStatsGuiComponent();
        addComponent(playerTankStatsGuiComponent);

        playerTankSingleplayerControllerComponent = new PlayerTankSingleplayerControllerComponent();
        addComponent(playerTankSingleplayerControllerComponent);

        collisionComponent = new Collision(armorComponent.getAnimation().mask(), CollidableObjectType.PLAYER_TANK);
        collisionComponent
                .addListener(CollidableObjectType.BOX, playerTankMovementControllerComponent::collision)
                .addListener(CollidableObjectType.ENEMY_TANK, playerTankMovementControllerComponent::collision)
                .addListener(DefaultCollidableObjectType.BORDER, playerTankMovementControllerComponent::collision)
                .addListener(CollidableObjectType.WALL, playerTankMovementControllerComponent::collision);
        addComponent(collisionComponent);
    }

    @Override
    public void exploded() {
        super.exploded();

        playerTankMovementControllerComponent.destroy();
        removeComponent(playerTankMovementControllerComponent);
        playerTankGunAttackControllerComponent.destroy();
        removeComponent(playerTankGunAttackControllerComponent);
        playerTankGunRotateControllerComponent.destroy();
        removeComponent(playerTankGunRotateControllerComponent);
        playerTankEquipmentControllerComponent.destroy();
        removeComponent(playerTankEquipmentControllerComponent);
        playerTankEquipmentGuiComponent.destroy();
        removeComponent(playerTankEquipmentGuiComponent);
        playerTankHpGuiComponent.destroy();
        removeComponent(playerTankHpGuiComponent);
        playerTankStatsGuiComponent.destroy();
        removeComponent(playerTankStatsGuiComponent);
    }

    @Override
    public void changeArmor(ArmorComponent newArmorComponent) {
        super.changeArmor(newArmorComponent);
        playerTankNetworkComponent.sendInfoAboutNewArmor(newArmorComponent);
    }

    @Override
    public void changeGun(GunComponent newGunComponent) {
        super.changeGun(newGunComponent);
        playerTankNetworkComponent.sendInfoAboutNewGun(newGunComponent);
    }
}
