package cc.abro.tow.client.tanks.equipment.gun;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class GunCreator<T extends GunSpecification> {

    public abstract GunComponent createGun(T gunSpecification, String name);
    public abstract Class<T> getGunSpecificationClass();
    public abstract String getType();

    protected Object getBulletBehavior(String behavior) {
        return null; //TODO
    }
}
