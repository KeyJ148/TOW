package cc.abro.tow.client.tanks.equipment.gun.defaults;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunCreator;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;

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
        Stats stats = Stats.builder()
                .setAttackSpeed(gunSpecification.getAttackSpeed())
                .setSpeedRotateGun(gunSpecification.getSpeedRotate())
                .setDamage(gunSpecification.getDamage())
                .setRange(gunSpecification.getRange())
                .build();
        Effect effect = new Effect();
        effect.setAddition(stats);
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
