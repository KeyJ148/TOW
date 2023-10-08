package cc.abro.tow.client.tanks.equipment;

import cc.abro.orchengine.context.GameService;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorCreator;
import cc.abro.tow.client.tanks.equipment.armor.fury.FuryArmorCreator;
import cc.abro.tow.client.tanks.equipment.armor.vampire.VampireArmorCreator;
import cc.abro.tow.client.tanks.equipment.gun.GunCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.gun.defaults.DefaultGunCreator;
import cc.abro.tow.client.tanks.equipment.gun.fury.FuryGunCreator;
import cc.abro.tow.client.tanks.equipment.gun.vampire.VampireGunCreator;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class EquipmentCreatorsLoader {

    private final ArmorCreatorsStorage armorCreatorsStorage;
    private final GunCreatorsStorage gunCreatorsStorage;

    public void load() {
        armorCreatorsStorage.addArmorCreator(new DefaultArmorCreator<>());
        armorCreatorsStorage.addArmorCreator(new FuryArmorCreator<>());
        armorCreatorsStorage.addArmorCreator(new VampireArmorCreator<>());

        gunCreatorsStorage.addGunCreator(new DefaultGunCreator<>());
        gunCreatorsStorage.addGunCreator(new FuryGunCreator<>());
        gunCreatorsStorage.addGunCreator(new VampireGunCreator<>());
    }
}
