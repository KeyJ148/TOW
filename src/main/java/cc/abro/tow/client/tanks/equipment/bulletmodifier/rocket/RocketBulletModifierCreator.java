package cc.abro.tow.client.tanks.equipment.bulletmodifier.rocket;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.StoredBulletModifierCreator;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierCreator;

@StoredBulletModifierCreator
public class RocketBulletModifierCreator<T extends RocketBulletModifierSpecification> extends DefaultBulletModifierCreator<T> {

    @Override
    public RocketBulletModifierComponent createBulletModifier(T bulletModifierSpecification, String name) {
        return new RocketBulletModifierComponent(name,
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
        return "rocket";
    }

    @Override
    public Class<T> getBulletModifierSpecificationClass() {
        return (Class<T>) RocketBulletModifierSpecification.class;
    }
}
