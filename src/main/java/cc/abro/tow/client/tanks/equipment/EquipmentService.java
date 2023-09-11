package cc.abro.tow.client.tanks.equipment;

import cc.abro.orchengine.context.GameService;
import cc.abro.tow.client.map.objects.Box;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreationService;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunCreationService;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class EquipmentService {

    private static final String DEFAULT_ARMOR_NAME = "default";
    private static final String DEFAULT_GUN_NAME = "default";

    private final ArmorCreationService armorCreationService;
    private final GunCreationService gunCreationService;

    public ArmorComponent createDefaultArmor() {
        return armorCreationService.createArmor(DEFAULT_ARMOR_NAME);
    }

    public GunComponent createGunArmor() {
        return gunCreationService.createGun(DEFAULT_GUN_NAME);
    }

    public void changeEquipment(Tank tank, Box box) {
        switch (box.getTypeBox()) {
            case 0 -> {
                if (player.takeArmor) EquipManager.newArmor(player);
            }
            case 1 -> {
                if (player.takeGun) EquipManager.newGun(player);
            }
            case 2 -> {
                if (player.takeBullet) EquipManager.newBullet(player);
            }
            case 3 -> {
                if (player.takeHealth) player.hp = (long) (player.hp + player.stats.hpMax * 0.4);
            }
            case 4 -> {
                if (player.takeHealth) player.hp = (long) player.stats.hpMax;
            }
        }
    }
}
