package cc.abro.tow.client.tanks.equipment.bullet;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.collision.CollidableComponent;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.tanks.equipment.armor.ADefault;
import cc.abro.tow.client.tanks.equipment.armor.AFury;
import cc.abro.tow.client.tanks.equipment.armor.AVampire;
import cc.abro.tow.client.tanks.player.Armor;
import cc.abro.tow.client.tanks.player.Bullet;
import cc.abro.tow.client.tanks.player.Player;

public class BMassSmall extends Bullet {

    @Override
    public void init(Player player, double x, double y, double dir, double damage, int range, String name) {
        super.init(player, x, y, dir, damage, range, name);

        getComponent(Collision.class).addCollidableObjects(new Class[]{ADefault.class, AFury.class, AVampire.class});
    }

    @Override
    public void collision(CollidableComponent collision) {
        if (isDestroyed()) return;
        GameObject gameObject = collision.getGameObject();

        if (gameObject instanceof Armor) {
            ((Armor) gameObject).player.hp -= damage;
            destroy(explosionSize);
        }

        super.collision(collision);
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
