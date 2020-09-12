package tech.abro.tow.client.tanks.equipment.bullet;

import tech.abro.orchengine.gameobject.GameObject;
import tech.abro.orchengine.gameobject.components.Collision;
import tech.abro.orchengine.gameobject.components.Movement;
import tech.abro.orchengine.setting.ConfigReader;
import tech.abro.tow.client.tanks.equipment.armor.ADefault;
import tech.abro.tow.client.tanks.equipment.armor.AFury;
import tech.abro.tow.client.tanks.equipment.armor.AVampire;
import tech.abro.tow.client.tanks.player.Armor;
import tech.abro.tow.client.tanks.player.Bullet;
import tech.abro.tow.client.tanks.player.Player;

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
