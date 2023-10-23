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

    public void takeBox(Tank tank, Box box) {
        PlayerTankEquipmentControllerComponent equipmentController =
                clientData.player.getPlayerTankEquipmentControllerComponent();

        switch (box.getType()) {
            case ARMOR -> {
                if (!equipmentController.isPlayerCanTakeArmor()) return;
                ArmorComponent armorComponent = equipmentService.createNewArmor(tank);
                tank.changeArmor(armorComponent);
            }
            case GUN -> {
                if (!equipmentController.isPlayerCanTakeGun()) return;
                GunComponent gunComponent = equipmentService.createNewGun(tank);
                tank.changeGun(gunComponent);
            }
            case BULLET -> {
                if (!equipmentController.isPlayerCanTakeBullet()) return;
                equipmentService.createNewBullet(tank);
            }
            case HEALTH -> {
                if (!equipmentController.isPlayerCanTakeHeal()) return;
                clientData.player.changeHp(clientData.player.getTankStatsComponent().getStats().getHpMax() * 0.4);
            }
            case HEALTH_FULL -> {
                if (!equipmentController.isPlayerCanTakeHeal()) return;
                clientData.player.changeHp(clientData.player.getTankStatsComponent().getStats().getHpMax());
            }
        }
    }
}
