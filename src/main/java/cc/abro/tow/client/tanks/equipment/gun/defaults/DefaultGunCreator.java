package cc.abro.tow.client.tanks.equipment.gun.defaults;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunCreator;
import cc.abro.tow.client.tanks.equipment.gun.StoredGunCreator;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@StoredGunCreator
public class DefaultGunCreator<T extends DefaultGunSpecification> extends GunCreator<T> {

    @Override
    public DefaultGunComponent createGun(T gunSpecification, String name) {
        return new DefaultGunComponent(name,
                gunSpecification.getTitle(),
                createEffect(gunSpecification),
                createSprite(gunSpecification),
                gunSpecification.getSoundShot(),
                createTrunksInfo(gunSpecification.getGunTrunksInfo()),
                createBulletMapping(gunSpecification.getBulletMapping()),
                gunSpecification.getSize(),
                gunSpecification.getTechLevel());
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
                .setBulletSpeed(gunSpecification.getBulletSpeed())
                .setBulletExplosionPower(gunSpecification.getBulletExplosionPower())
                .build();
        Effect effect = new Effect();
        effect.setAddition(stats);
        return effect;
    }

    protected Sprite createSprite(T gunSpecification) {
        return Context.getService(SpriteStorage.class).getSprite(gunSpecification.getSpriteName());
    }

    protected List<GunComponent.TrunkInfo> createTrunksInfo(List<DefaultGunSpecification.TrunkInfo> trunksInfo) {
        return trunksInfo.stream()
                .map(trunkInfo -> new GunComponent.TrunkInfo(
                        trunkInfo.bulletStartX(), trunkInfo.bulletStartY(), trunkInfo.bulletStartDir()
                ))
                .toList();
    }

    protected Map<String, GunComponent.BulletInfo> createBulletMapping(
            Map<String, DefaultGunSpecification.BulletInfo> bulletMapping) {
        return bulletMapping.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new GunComponent.BulletInfo(
                                getBulletCreator(e.getValue().bulletName()),
                                e.getValue().spriteName(),
                                e.getValue().soundHit()
                        )
                ));
    }

    protected BulletCreator getBulletCreator(String name) {
        return Context.getService(BulletCreatorsStorage.class).getBulletCreator(name);
    }

}
