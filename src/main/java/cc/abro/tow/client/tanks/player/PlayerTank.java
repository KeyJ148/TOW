package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.gameobject.components.collision.DefaultCollidableObjectType;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.components.PlayerTankControllerComponent;
import cc.abro.tow.client.tanks.components.PlayerTankNetworkComponent;
import cc.abro.tow.client.tanks.components.TankVampireComponent;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;

public class PlayerTank extends Tank {

    protected final Collision collisionComponent;
    protected final TankVampireComponent tankVampireComponent;
    protected final PlayerTankNetworkComponent playerTankNetworkComponent;
    protected final PlayerTankControllerComponent playerTankControllerComponent;

    public PlayerTank(Location location, double x, double y, double direction,
                      ArmorComponent armorComponent, GunComponent gunComponent) {
        super(location, x, y, direction, armorComponent, gunComponent);

        tankVampireComponent = new TankVampireComponent();
        addComponent(tankVampireComponent);

        playerTankNetworkComponent = new PlayerTankNetworkComponent();
        addComponent(playerTankNetworkComponent);

        playerTankControllerComponent = new PlayerTankControllerComponent();
        addComponent(playerTankControllerComponent);

        collisionComponent = new Collision(armorComponent.getAnimation().mask(), CollidableObjectType.ARMOR);
        collisionComponent.addListener(CollidableObjectType.BOX, playerTankControllerComponent::collision)
                .addListener(CollidableObjectType.ENEMY_ARMOR, playerTankControllerComponent::collision)
                .addListener(DefaultCollidableObjectType.BORDER, playerTankControllerComponent::collision)
                .addListener(CollidableObjectType.WALL, playerTankControllerComponent::collision);
        addComponent(collisionComponent);
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
