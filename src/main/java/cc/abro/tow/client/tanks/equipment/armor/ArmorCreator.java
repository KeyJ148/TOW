package cc.abro.tow.client.tanks.equipment.armor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class ArmorCreator<T extends ArmorSpecification> {

    public abstract ArmorComponent createArmor(T armorSpecification, String name);
    public abstract Class<T> getArmorSpecificationClass();
    public abstract String getType();
}
