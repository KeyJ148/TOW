package cc.abro.tow.client.tanks.equipment.bulletbehaviors;

@StoredBulletBehavior
public class DefaultBulletBehavior implements BulletBehavior{

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public BulletBehavior createInstance() {
        return new DefaultBulletBehavior();
    }
}
