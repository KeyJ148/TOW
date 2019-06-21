package game.client.tanks.equipment.bullet;

import engine.Global;
import engine.Loader;
import engine.io.Logger;
import engine.obj.Obj;
import engine.setting.ConfigReader;
import game.client.map.Wall;
import game.client.tanks.enemy.EnemyArmor;
import game.client.tanks.player.Bullet;

import java.util.Random;

public class BMass extends Bullet {

    public int minFragmentNumber;
    public int maxFragmentNumber;
    public String configName;

    @Override
    public void collision(Obj obj){
        if (destroy) return;

        if (obj.getClass().equals(Wall.class) || obj.getClass().equals(EnemyArmor.class)) {
            Random random = new Random();
            int count = minFragmentNumber + random.nextInt(maxFragmentNumber - minFragmentNumber + 1);

            String newBulletClassName = new ConfigReader(Bullet.PATH_SETTING + configName + ".properties").findString("CLASS");
            String newBulletFullName = BDefault.class.getPackage().getName() + "." + newBulletClassName;

            try {
                for (int i = 0; i < count; i++) {
                    Bullet newBullet = (Bullet) Class.forName(newBulletFullName).newInstance();
                    newBullet.init(
                            player,
                            movement.getXPrevious(),
                            movement.getYPrevious(),
                            random.nextInt(360),
                            0,
                            0,
                            configName
                    );

                    Global.room.objAdd(newBullet);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                Logger.println("Bullet create error: " + configName, Logger.Type.ERROR);
                Loader.exit();
            }
        }

        //Обработка столкновения родителя
        //Обязательно в конце, иначе сразу выйдет из метода, т.к. destroy = true
        super.collision(obj);
    }

    @Override
    public void loadData(){
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        minFragmentNumber = cr.findInteger("MIN_FRAGMENT_NUMBER");
        maxFragmentNumber = cr.findInteger("MAX_FRAGMENT_NUMBER");
        configName = cr.findString("CONFIG_NAME");
    }

}

