package cc.abro.tow.client.tanks.equipment.bulletbehaviors.defaults;

import cc.abro.tow.client.tanks.equipment.bulletbehaviors.BulletBehavior;
import cc.abro.tow.client.tanks.equipment.bulletbehaviors.BulletBehaviorCreator;
import cc.abro.tow.client.tanks.equipment.bulletbehaviors.StoredBulletBehaviorCreator;

@StoredBulletBehaviorCreator
public class DefaultBulletBehaviorCreator implements BulletBehaviorCreator {

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public BulletBehavior createBulletBehavior() {
        return new DefaultBulletBehavior();
    }
}
