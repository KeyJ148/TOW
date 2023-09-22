package cc.abro.tow.client.tanks.equipment.bullet0;

import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.tanks.player.PlayerTank;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BulletFactory {

    public String name, title; //name - техническое название, title - игровое
    public PlayerTank player;

    public BulletFactory(String name, PlayerTank player) {
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
            //log.error("Bullet create error: " + player.bullet.name);
            throw new RuntimeException(e);
        }
    }
}
