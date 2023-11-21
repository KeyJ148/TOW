package cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.BulletModifierCreator;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.StoredBulletModifierCreator;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;

@StoredBulletModifierCreator
public class DefaultBulletModifierCreator<T extends DefaultBulletModifierSpecification> extends BulletModifierCreator<T> {

    @Override
    public DefaultBulletModifierComponent createBulletModifier(T bulletModifierSpecification, String name) {
        return new DefaultBulletModifierComponent(name,
                bulletModifierSpecification.getTitle(),
                createEffect(bulletModifierSpecification),
                bulletModifierSpecification.getTechLevel());
    }

    @Override
    public String getType() {
        return "default";
    }

    @Override
    public Class<T> getBulletModifierSpecificationClass() {
        return (Class<T>) DefaultBulletModifierSpecification.class;
    }

    protected Effect createEffect(T bulletSpecification) {
        Stats stats = Stats.builder().setAllValue(1.0)
                .setBulletSpeed(bulletSpecification.getSpeed())
                .setDamage(bulletSpecification.getDamage())
                .setRange(bulletSpecification.getRange())
                .setBulletExplosionPower(bulletSpecification.getExplosionPower())
                .build();
        Effect effect = new Effect();
        effect.setMulti(stats);
        return effect;
    }
}
