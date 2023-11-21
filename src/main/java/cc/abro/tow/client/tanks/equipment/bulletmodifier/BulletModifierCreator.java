package cc.abro.tow.client.tanks.equipment.bulletmodifier;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class BulletModifierCreator<T extends BulletModifierSpecification> {

    public abstract BulletModifierComponent createBulletModifier(T bulletModifierSpecification, String name);
    public abstract Class<T> getBulletModifierSpecificationClass();
    public abstract String getType();
}
