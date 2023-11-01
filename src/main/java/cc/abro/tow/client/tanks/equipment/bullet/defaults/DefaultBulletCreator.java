package cc.abro.tow.client.tanks.equipment.bullet.defaults;

import cc.abro.tow.client.tanks.equipment.bullet.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.StoredBulletCreator;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;

@StoredBulletCreator
public class DefaultBulletCreator<T extends DefaultBulletSpecification> extends BulletCreator<T> {

    @Override
    public DefaultBulletComponent createBullet(T bulletSpecification, String name) {
        return new DefaultBulletComponent(name,
                bulletSpecification.getTitle(),
                createEffect(bulletSpecification));
    }

    @Override
    public String getType() {
        return "default";
    }

    @Override
    public Class<T> getBulletSpecificationClass() {
        return (Class<T>) DefaultBulletSpecification.class;
    }

    protected Effect createEffect(T bulletSpecification) {
        Stats stats = Stats.builder()
                .setBulletSpeed(bulletSpecification.getSpeed())
                .setDamage(bulletSpecification.getDamage())
                .setRange(bulletSpecification.getRange())
                .setBulletExplosionPower(bulletSpecification.getExplosionPower())
                .build();
        Effect effect = new Effect();
        effect.setAddition(stats);
        return effect;
    }
}
