package cc.abro.tow.client.tanks.equipment;

import cc.abro.orchengine.context.GameService;
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

    public void setNewArmor(Tank tank) {
        //TODO
    }

    public void setNewGun(Tank tank) {
        //TODO
    }

    public void setNewBullet(Tank tank) {
        //TODO
    }

    public ArmorComponent createDefaultArmor() {
        return armorCreationService.createArmor(DEFAULT_ARMOR_NAME);
    }

    public GunComponent createDefaultGun() {
        return gunCreationService.createGun(DEFAULT_GUN_NAME);
    }

    public ArmorComponent createArmor(String armorName) {
        return armorCreationService.createArmor(armorName);
    }

    public GunComponent createGun(String gunName) {
        return gunCreationService.createGun(gunName);
    }
}
