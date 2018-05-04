package game.client.tanks.equipment.bullet;

import engine.obj.Obj;
import engine.setting.ConfigReader;
import game.client.tanks.equipment.armor.ADefault;
import game.client.tanks.equipment.armor.AFury;
import game.client.tanks.equipment.armor.AVampire;
import game.client.tanks.player.Armor;
import game.client.tanks.player.Bullet;
import game.client.tanks.player.Player;

public class BMassSmall extends Bullet{

    @Override
    public void init(Player player, double x, double y, double dir, double damage, int range, String name){
        super.init(player, x, y, dir, damage, range, name);

        this.collision.addCollisionObjects(new Class[] {ADefault.class, AFury.class, AVampire.class});
    }

    @Override
    public void collision(Obj obj){
        if (destroy) return;

        if (obj instanceof Armor){
            ((Armor) obj).player.hp -= damage;
            destroy();
        }

        super.collision(obj);
    }

    @Override
    public void playSoundShot(){}

    @Override
    public void loadData(){
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        movement.speed = cr.findDouble("SPEED");
    }
}
