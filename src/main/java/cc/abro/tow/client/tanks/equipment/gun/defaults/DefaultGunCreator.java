package cc.abro.tow.client.tanks.equipment.gun.defaults;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunCreator;
import cc.abro.tow.client.tanks.stats.Effect;

public class DefaultGunCreator<T extends DefaultGunSpecification> extends GunCreator<T> {

    @Override
    public DefaultGunComponent createGun(T gunSpecification, String name) {
        return new DefaultGunComponent(name,
                gunSpecification.getType(),
                createEffect(gunSpecification),
                createSprite(gunSpecification),
                gunSpecification.getGunTrunksInfo().stream().map(this::createTrunkInfo).toList());
    }

    @Override
    public String getType() {
        return "default";
    }

    @Override
    public Class<T> getGunSpecificationClass() {
        return (Class<T>) DefaultGunSpecification.class;
    }

    protected Effect createEffect(T gunSpecification) {
        Effect effect = new Effect();
        effect.addition.attackSpeed = gunSpecification.getAttackSpeed();
        effect.addition.speedRotateGun = gunSpecification.getSpeedRotate();
        effect.addition.damage = gunSpecification.getDamage();
        effect.addition.range = gunSpecification.getRange();
        return effect;
    }

    protected Sprite createSprite(T gunSpecification) {
        return Context.getService(SpriteStorage.class).getSprite(gunSpecification.getSpriteName());
    }

    protected GunComponent.TrunkInfo createTrunkInfo(DefaultGunSpecification.TrunkInfo trunkInfo) {
        return new GunComponent.TrunkInfo(
                trunkInfo.bulletStartX(), trunkInfo.bulletStartY(), trunkInfo.bulletStartDir()
        );
    }
}
