package cc.abro.tow.client.tanks.equipment.armor;

import cc.abro.orchengine.context.GameService;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class ArmorCreationService {

    private final ArmorSpecificationStorage armorSpecificationStorage;
    private final ArmorCreatorsStorage armorCreatorsStorage;

    public ArmorComponent createArmor(String name) {
        ArmorSpecification armorSpecification = armorSpecificationStorage.getArmorSpecification(name);
        ArmorCreator armorCreator = armorCreatorsStorage.getArmorCreator(armorSpecification.getType());
        return armorCreator.createArmor(armorSpecification, name);
    }
}
