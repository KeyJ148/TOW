package cc.abro.tow.client.tanks.equipment.bullet1.defaults;

import cc.abro.tow.client.tanks.equipment.bullet1.Bullet;
import cc.abro.tow.client.tanks.equipment.bullet1.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet1.StoredBulletCreator;

@StoredBulletCreator
public class DefaultBulletCreator implements BulletCreator {

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public Bullet createBullet() {
        return new DefaultBullet();
    }
}
