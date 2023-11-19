package cc.abro.tow.client.tanks.equipment.armor.vampire;

import cc.abro.tow.client.tanks.equipment.armor.StoredArmorCreator;
import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorCreator;

@StoredArmorCreator
public class VampireArmorCreator<T extends VampireArmorSpecification> extends DefaultArmorCreator<T> {

    @Override
    public VampireArmorComponent createArmor(T armorSpecification, String name) {
        return new VampireArmorComponent(name,
                armorSpecification.getTitle(),
                createEffect(armorSpecification),
                armorSpecification.getAnimationSpeedCoefficient(),
                createAnimation(armorSpecification),
                armorSpecification.getSize(),
                armorSpecification.getTechLevel(),
                armorSpecification.getSpeedUpMax(),
                armorSpecification.getSpeedUpMin(),
                armorSpecification.getSpeedDownMax(),
                armorSpecification.getSpeedDownMin(),
                armorSpecification.getSpeedRotateTankMin(),
                armorSpecification.getSpeedRotateTankMax()
        );
    }

    @Override
    public String getType() {
        return "vampire";
    }

    @Override
    public Class<T> getArmorSpecificationClass() {
        return (Class<T>) VampireArmorSpecification.class;
    }
}
