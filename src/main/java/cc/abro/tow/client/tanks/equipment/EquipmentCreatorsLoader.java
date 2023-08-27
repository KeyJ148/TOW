package cc.abro.tow.client.tanks.equipment;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.services.AnnotationScanService;
import cc.abro.orchengine.util.ReflectionUtils;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreator;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorCreator;
import cc.abro.tow.client.tanks.equipment.armor.fury.FuryArmorCreator;
import cc.abro.tow.client.tanks.equipment.armor.vampire.VampireArmorCreator;
import cc.abro.tow.client.tanks.equipment.gun.GunCreator;
import cc.abro.tow.client.tanks.equipment.gun.GunCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.gun.defaults.DefaultGunCreator;
import cc.abro.tow.client.tanks.equipment.gun.fury.FuryGunCreator;
import cc.abro.tow.client.tanks.equipment.gun.vampire.VampireGunCreator;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;


@GameService
@RequiredArgsConstructor
public class EquipmentCreatorsLoader {

    private final ArmorCreatorsStorage armorCreatorsStorage;
    private final GunCreatorsStorage gunCreatorsStorage;
    private final AnnotationScanService annotationScanService;

    public void load() {
        var armorCreators = ReflectionUtils.CreateInstances(
                annotationScanService.getClassesWithAnnotations(StoredArmorCreator.class));

        for(var armorCreator : armorCreators){
            if(armorCreator instanceof ArmorCreator creator)
                armorCreatorsStorage.addArmorCreator(creator);
        }


        var gunCreators = ReflectionUtils.CreateInstances(
                annotationScanService.getClassesWithAnnotations(StoredGunCreator.class));

        for(var gunCreator : gunCreators){
            if(gunCreator instanceof GunCreator creator)
                gunCreatorsStorage.addGunCreator(creator);
        }
    }
}
