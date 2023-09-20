package cc.abro.tow.client.tanks.equipment.bullet0;

import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.collision.CollidableComponent;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.gameobject.components.collision.CollisionType;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.tanks.player0.Player;

public class BMassSmall extends Bullet {

    @Override
    public void init(Player player, double x, double y, double dir, double damage, int range, String name) {
        super.init(player, x, y, dir, damage, range, name);

        getComponent(Collision.class).addListener(CollidableObjectType.PLAYER_TANK, this);
    }

    @Override
    public void collision(CollidableComponent collision, CollisionType collisionType) {
        if (isDestroyed()) return;
        if (collisionType == CollisionType.LEAVING) return;

        if (collision.getType() == CollidableObjectType.PLAYER_TANK) {
            //collision.getGameObject().player.hp -= damage;
            destroy(explosionSize);
        }

        super.collision(collision, collisionType);
    }

    @Override
    public void playSoundShot() {
    }

    @Override
    public void loadData() {
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        getComponent(Movement.class).speed = cr.findDouble("SPEED");
    }
}
