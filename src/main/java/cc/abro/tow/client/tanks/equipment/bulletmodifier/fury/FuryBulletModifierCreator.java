package cc.abro.tow.client.tanks.equipment.bulletmodifier.fury;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.StoredBulletModifierCreator;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierCreator;

@StoredBulletModifierCreator
public class FuryBulletModifierCreator<T extends FuryBulletModifierSpecification> extends DefaultBulletModifierCreator<T> {

    @Override
    public FuryBulletModifierComponent createBulletModifier(T bulletModifierSpecification, String name) {
        return new FuryBulletModifierComponent(name,
                bulletModifierSpecification.getTitle(),
                createEffect(bulletModifierSpecification),
                bulletModifierSpecification.getTechLevel(),
                bulletModifierSpecification.getMinSpeed(),
                bulletModifierSpecification.getMaxSpeed(),
                bulletModifierSpecification.getMinDamage(),
                bulletModifierSpecification.getMaxDamage());
    }

    @Override
    public String getType() {
        return "fury";
    }

    @Override
    public Class<T> getBulletModifierSpecificationClass() {
        return (Class<T>) FuryBulletModifierSpecification.class;
    }
}
