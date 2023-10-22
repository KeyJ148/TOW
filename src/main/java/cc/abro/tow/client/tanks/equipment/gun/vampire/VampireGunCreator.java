package cc.abro.tow.client.tanks.equipment.gun.vampire;

import cc.abro.tow.client.tanks.equipment.StoredGunCreator;
import cc.abro.tow.client.tanks.equipment.gun.defaults.DefaultGunCreator;

@StoredGunCreator
public class VampireGunCreator<T extends VampireGunSpecification> extends DefaultGunCreator<T> {

    @Override
    public VampireGunComponent createGun(T gunSpecification, String name) {
        return new VampireGunComponent(name,
                gunSpecification.getType(),
                createEffect(gunSpecification),
                createSprite(gunSpecification),
                gunSpecification.getGunTrunksInfo().stream().map(this::createTrunkInfo).toList(),
                gunSpecification.getSize(),
                gunSpecification.getAttackSpeedMin(),
                gunSpecification.getAttackSpeedMax());
    }

    @Override
    public String getType() {
        return "vampire";
    }

    @Override
    public Class<T> getGunSpecificationClass() {
        return (Class<T>) VampireGunSpecification.class;
    }
}
