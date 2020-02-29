package tow.game.client.tanks.equipment.bullet;

import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Collision;
import tow.engine.gameobject.components.Movement;
import tow.engine.setting.ConfigReader;
import tow.game.client.tanks.equipment.armor.ADefault;
import tow.game.client.tanks.equipment.armor.AFury;
import tow.game.client.tanks.equipment.armor.AVampire;
import tow.game.client.tanks.player.Armor;
import tow.game.client.tanks.player.Bullet;
import tow.game.client.tanks.player.Player;

public class BMassSmall extends Bullet{

    @Override
    public void init(Player player, double x, double y, double dir, double damage, int range, String name){
        super.init(player, x, y, dir, damage, range, name);

        getComponent(Collision.class).addCollisionObjects(new Class[] {ADefault.class, AFury.class, AVampire.class});
    }

    @Override
    public void collision(GameObject gameObject){
        if (isDestroy()) return;

        if (gameObject instanceof Armor){
            ((Armor) gameObject).player.hp -= damage;
            destroy();
        }

        super.collision(gameObject);
    }

    @Override
    public void playSoundShot(){}

    @Override
    public void loadData(){
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        getComponent(Movement.class).speed = cr.findDouble("SPEED");
    }
}
