package cc.abro.tow.client.tanks.equipment.armor.fury;

import cc.abro.tow.client.tanks.equipment.StoredArmorCreator;
import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorCreator;

@StoredArmorCreator
public class FuryArmorCreator<T extends FuryArmorSpecification> extends DefaultArmorCreator<T> {

    @Override
    public FuryArmorComponent createArmor(T armorSpecification, String name) {
        return new FuryArmorComponent(name,
                armorSpecification.getTitle(),
                createEffect(armorSpecification),
                armorSpecification.getAnimationSpeedCoefficient(),
                createAnimation(armorSpecification),
                armorSpecification.getSize(),
                armorSpecification.getSpeedUpMax(),
                armorSpecification.getSpeedUpMin(),
                armorSpecification.getSpeedDownMax(),
                armorSpecification.getSpeedDownMin()
        );
    }

    @Override
    public String getType() {
        return "fury";
    }

    @Override
    public Class<T> getArmorSpecificationClass() {
        return (Class<T>) FuryArmorSpecification.class;
    }
}
