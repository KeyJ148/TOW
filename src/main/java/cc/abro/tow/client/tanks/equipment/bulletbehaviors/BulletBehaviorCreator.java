package cc.abro.tow.client.tanks.equipment.bulletbehaviors;

public interface BulletBehaviorCreator {

    String getName();
    BulletBehavior createBulletBehavior();
}
