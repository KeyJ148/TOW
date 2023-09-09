package cc.abro.tow.client.tanks.enemy;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.tanks.Tank;

public class EnemyTank extends Tank {

    public EnemyTank(Location location) {
        super(location, "BDefault", "GDefault");
        ArmorLoader armorLoader = new ArmorLoader("BDefault");

        addComponent(new Collision(armorLoader.textureHandlers.mask(), CollidableObjectType.ENEMY_ARMOR));
    }
}
