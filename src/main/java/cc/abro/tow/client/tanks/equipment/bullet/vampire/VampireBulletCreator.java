package cc.abro.tow.client.tanks.equipment.bullet.vampire;

import cc.abro.tow.client.tanks.equipment.bullet.StoredBulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.defaults.DefaultBulletCreator;

@StoredBulletCreator
public class VampireBulletCreator<T extends VampireBulletSpecification> extends DefaultBulletCreator<T> {

    @Override
    public VampireBulletComponent createBullet(T bulletSpecification, String name) {
        return new VampireBulletComponent(name,
                bulletSpecification.getTitle(),
                createEffect(bulletSpecification),
                bulletSpecification.getTechLevel(),
                bulletSpecification.getMinDamage(),
                bulletSpecification.getMaxDamage());
    }

    @Override
    public String getType() {
        return "fury";
    }

    @Override
    public Class<T> getBulletSpecificationClass() {
        return (Class<T>) VampireBulletSpecification.class;
    }
}
