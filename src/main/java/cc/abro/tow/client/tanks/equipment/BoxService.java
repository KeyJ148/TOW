package cc.abro.tow.client.tanks.equipment;

import cc.abro.orchengine.context.GameService;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.map.objects.Box;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.components.PlayerTankEquipmentControllerComponent;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class BoxService {

    private final ClientData clientData;
    private final EquipmentService equipmentService;

    public void takeBox(Tank tank, Box.Type boxType) {
        PlayerTankEquipmentControllerComponent equipmentController =
                clientData.player.getPlayerTankEquipmentControllerComponent();

        switch (boxType) {
            case ARMOR -> {
                if (!equipmentController.isPlayerCanTakeArmor()) return;
                takeArmorBox(tank);
            }
            case GUN -> {
                if (!equipmentController.isPlayerCanTakeGun()) return;
                takeGunBox(tank);
            }
            case BULLET -> {
                if (!equipmentController.isPlayerCanTakeBullet()) return;
                takeBulletBox(tank);
            }
            case HEALTH -> {
                if (!equipmentController.isPlayerCanTakeHeal()) return;
                takeHealthBox(tank);
            }
            case HEALTH_FULL -> {
                if (!equipmentController.isPlayerCanTakeHeal()) return;
                takeHealthFullBox(tank);
            }
        }
    }

    public void takeArmorBox(Tank tank) {
        ArmorComponent armorComponent = equipmentService.createNewArmor(tank);
        tank.changeArmor(armorComponent);
    }

    public void takeGunBox(Tank tank) {
        GunComponent gunComponent = equipmentService.createNewGun(tank);
        tank.changeGun(gunComponent);
    }

    public void takeBulletBox(Tank tank) {
        equipmentService.createNewBullet(tank);
    }

    public void takeHealthBox(Tank tank) {
        clientData.player.changeHp(clientData.player.getTankStatsComponent().getStats().getHpMax() * 0.4);
    }

    public void takeHealthFullBox(Tank tank) {
        clientData.player.changeHp(clientData.player.getTankStatsComponent().getStats().getHpMax());
    }
}
