package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Loader;
import cc.abro.orchengine.logger.Logger;
import cc.abro.orchengine.setting.ConfigReader;
import cc.abro.tow.client.tanks.equipment.bullet.BDefault;

public class BulletFactory {

    public String name, title; //name - техническое название, title - игровое
    public Player player;

    public BulletFactory(String name, Player player) {
        this.name = name;
        this.player = player;

        title = new ConfigReader(Bullet.PATH_SETTING + name + ".properties").findString("TITLE");
    }

    //Создание класса через рефлексию
    public Bullet create() {
        try {
            String newBulletClassName = new ConfigReader(Bullet.PATH_SETTING + name + ".properties").findString("CLASS");
            String newBulletFullName = BDefault.class.getPackage().getName() + "." + newBulletClassName;
            Bullet newBullet = (Bullet) Class.forName(newBulletFullName).newInstance();

            return newBullet;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            Global.logger.println("Bullet create error: " + player.bullet.name, Logger.Type.ERROR);
            Loader.exit();
        }

        return null;
    }
}
