package cc.abro.tow.client.tanks.equipment.bullet0;

import cc.abro.tow.client.tanks.player.PlayerTank;

public class BMassSmall extends Bullet {

    @Override
    public void init(PlayerTank player, double x, double y, double dir, double damage, int range, String name) {
        super.init(player, x, y, dir, damage, range, name);

        //getComponent(Collision.class).addListener(CollidableObjectType.PLAYER_TANK, this);
    }

    /*@Override
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
        getComponent(Movement.class).setSpeed(cr.findDouble("SPEED"));
    }*/
}
