package cc.abro.tow.client.tanks.equipment.bullet;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class BulletCreator<T extends BulletSpecification> {

    public abstract BulletComponent createBullet(T bulletSpecification, String name);
    public abstract Class<T> getBulletSpecificationClass();
    public abstract String getType();
}
