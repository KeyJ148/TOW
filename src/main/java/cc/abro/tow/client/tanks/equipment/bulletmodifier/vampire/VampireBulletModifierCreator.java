package cc.abro.tow.client.tanks.equipment.bulletmodifier.vampire;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.StoredBulletModifierCreator;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierCreator;

@StoredBulletModifierCreator
public class VampireBulletModifierCreator<T extends VampireBulletModifierSpecification> extends DefaultBulletModifierCreator<T> {

    @Override
    public VampireBulletModifierComponent createBulletModifier(T bulletModifierSpecification, String name) {
        return new VampireBulletModifierComponent(name,
                bulletModifierSpecification.getTitle(),
                createEffect(bulletModifierSpecification),
                bulletModifierSpecification.getTechLevel(),
                bulletModifierSpecification.getMinDamage(),
                bulletModifierSpecification.getMaxDamage());
    }

    @Override
    public String getType() {
        return "vampire";
    }

    @Override
    public Class<T> getBulletModifierSpecificationClass() {
        return (Class<T>) VampireBulletModifierSpecification.class;
    }
}
