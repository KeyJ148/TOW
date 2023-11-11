package cc.abro.tow.client.tanks.equipment.bullet.fury;

import cc.abro.tow.client.tanks.equipment.bullet.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.StoredBulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.defaults.DefaultBulletCreator;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;

@StoredBulletCreator
public class FuryBulletCreator<T extends FuryBulletSpecification> extends DefaultBulletCreator<T> {

    @Override
    public FuryBulletComponent createBullet(T bulletSpecification, String name) {
        return new FuryBulletComponent(name,
                bulletSpecification.getTitle(),
                createEffect(bulletSpecification),
                bulletSpecification.getTechLevel(),
                bulletSpecification.getMinSpeed(),
                bulletSpecification.getMaxSpeed(),
                bulletSpecification.getMinDamage(),
                bulletSpecification.getMaxDamage());
    }

    @Override
    public String getType() {
        return "fury";
    }

    @Override
    public Class<T> getBulletSpecificationClass() {
        return (Class<T>) FuryBulletSpecification.class;
    }
}
