package cc.abro.tow.client.tanks.equipment.armor.defaults;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.orchengine.resources.animations.AnimationStorage;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreator;
import cc.abro.tow.client.tanks.stats.Effect;

public class DefaultArmorCreator<T extends DefaultArmorSpecification> extends ArmorCreator<T> {

    @Override
    public DefaultArmorComponent createArmor(T armorSpecification, String name) {
        return new DefaultArmorComponent(name,
                armorSpecification.getType(),
                createEffect(armorSpecification),
                armorSpecification.getAnimationSpeedCoefficient(),
                createAnimation(armorSpecification));
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
        Effect effect = new Effect();
        effect.addition.hpMax = armorSpecification.getHpMax();
        effect.addition.hpRegen = armorSpecification.getHpRegen();
        effect.addition.speedUp = armorSpecification.getSpeedUp();
        effect.addition.speedDown = armorSpecification.getSpeedDown();
        effect.addition.speedRotateGun = armorSpecification.getSpeedRotateGun();
        effect.addition.speedRotateTank = armorSpecification.getSpeedRotateTank();
        effect.addition.stability = armorSpecification.getStability();
        return effect;
    }

    protected Animation createAnimation(T armorSpecification) {
        return Context.getService(AnimationStorage.class).getAnimation(armorSpecification.getAnimationName());
    }
}
