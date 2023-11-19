package cc.abro.tow.client.tanks.equipment.gun.fury;

import cc.abro.tow.client.tanks.equipment.gun.StoredGunCreator;
import cc.abro.tow.client.tanks.equipment.gun.defaults.DefaultGunCreator;

@StoredGunCreator
public class FuryGunCreator<T extends FuryGunSpecification> extends DefaultGunCreator<T> {

    @Override
    public FuryGunComponent createGun(T gunSpecification, String name) {
        return new FuryGunComponent(name,
                gunSpecification.getTitle(),
                createEffect(gunSpecification),
                createSprite(gunSpecification),
                gunSpecification.getSoundShot(),
                createTrunksInfo(gunSpecification.getGunTrunksInfo()),
                createBulletMapping(gunSpecification.getBulletMapping()),
                gunSpecification.getSize(),
                gunSpecification.getTechLevel(),
                gunSpecification.getAttackSpeedMin(),
                gunSpecification.getAttackSpeedMax());
    }

    @Override
    public String getType() {
        return "fury";
    }

    @Override
    public Class<T> getGunSpecificationClass() {
        return (Class<T>) FuryGunSpecification.class;
    }
}
