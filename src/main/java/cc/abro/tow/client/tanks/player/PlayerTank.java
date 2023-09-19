package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.gameobject.components.collision.DefaultCollidableObjectType;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.components.*;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
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

    public PlayerTank(Location location, double x, double y, double direction,
                      ArmorComponent armorComponent, GunComponent gunComponent) {
        super(location, x, y, direction, armorComponent, gunComponent);

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

        collisionComponent = new Collision(armorComponent.getAnimation().mask(), CollidableObjectType.PLAYER_TANK);
        collisionComponent.addListener(CollidableObjectType.BOX, playerTankMovementControllerComponent::collision)
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
