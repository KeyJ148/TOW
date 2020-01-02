package tow.game.client.tanks.player;

import tow.engine.Global;
import tow.engine.io.logger.Logger;
import tow.engine2.Loader;
import tow.engine3.setting.ConfigReader;
import tow.game.client.tanks.equipment.bullet.BDefault;

public class BulletFactory {

    public String name, title; //name - техническое название, title - игровое
    public Player player;

    public BulletFactory(String name, Player player){
        this.name = name;
        this.player = player;

        title = new ConfigReader(Bullet.PATH_SETTING + name + ".properties").findString("TITLE");
    }

    //Создание класса через рефлексию
    public Bullet create(){
        try{
            String newBulletClassName = new ConfigReader(Bullet.PATH_SETTING + name + ".properties").findString("CLASS");
            String newBulletFullName = BDefault.class.getPackage().getName() + "." + newBulletClassName;
            Bullet newBullet = (Bullet) Class.forName(newBulletFullName).newInstance();

            return newBullet;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
            Global.logger.println("Bullet create error: " + player.bullet.name, Logger.Type.ERROR);
            Loader.exit();
        }

        return null;
    }
}
