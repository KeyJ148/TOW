package cc.abro.tow.client.tanks.equipment.armor.defaults;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.orchengine.resources.animations.AnimationStorage;
import cc.abro.tow.client.tanks.equipment.StoredArmorCreator;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreator;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;

@StoredArmorCreator
public class DefaultArmorCreator<T extends DefaultArmorSpecification> extends ArmorCreator<T> {

    @Override
    public DefaultArmorComponent createArmor(T armorSpecification, String name) {
        return new DefaultArmorComponent(name,
                armorSpecification.getType(),
                createEffect(armorSpecification),
                armorSpecification.getAnimationSpeedCoefficient(),
                createAnimation(armorSpecification),
                armorSpecification.getSize());
    }

    @Override
    public String getType() {
        return "default";
    }

    @Override
    public Class<T> getArmorSpecificationClass() {
        return (Class<T>) DefaultArmorSpecification.class;
    }

    protected Effect createEffect(T armorSpecification) {
        Stats stats = Stats.builder()
                .setHpMax(armorSpecification.getHpMax())
                .setHpRegen(armorSpecification.getHpRegen())
                .setSpeedUp(armorSpecification.getSpeedUp())
                .setSpeedDown(armorSpecification.getSpeedDown())
                .setSpeedRotateGun(armorSpecification.getSpeedRotateGun())
                .setSpeedRotateTank(armorSpecification.getSpeedRotateTank())
                .setStability(armorSpecification.getStability())
                .build();
        Effect effect = new Effect();
        effect.setAddition(stats);
        return effect;
    }

    protected Animation createAnimation(T armorSpecification) {
        return Context.getService(AnimationStorage.class).getAnimation(armorSpecification.getAnimationName());
    }
}
