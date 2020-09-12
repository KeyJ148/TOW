package tech.abro.tow.client.tanks.equipment.bullet;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.Loader;
import tech.abro.orchengine.gameobject.GameObject;
import tech.abro.orchengine.gameobject.components.Movement;
import tech.abro.orchengine.logger.Logger;
import tech.abro.orchengine.setting.ConfigReader;
import tech.abro.tow.client.map.objects.textured.TexturedMapObject;
import tech.abro.tow.client.tanks.enemy.EnemyArmor;
import tech.abro.tow.client.tanks.player.Bullet;

import java.util.Random;

public class BMass extends Bullet {

    public int minFragmentNumber;
    public int maxFragmentNumber;
    public String configName;

    @Override
    public void collision(GameObject gameObject){
        if (isDestroy()) return;

        if (gameObject.getClass().equals(TexturedMapObject.class) || gameObject.getClass().equals(EnemyArmor.class)) {
            Random random = new Random();
            int count = minFragmentNumber + random.nextInt(maxFragmentNumber - minFragmentNumber + 1);

            String newBulletClassName = new ConfigReader(Bullet.PATH_SETTING + configName + ".properties").findString("CLASS");
            String newBulletFullName = BDefault.class.getPackage().getName() + "." + newBulletClassName;

            try {
                for (int i = 0; i < count; i++) {
                    Bullet newBullet = (Bullet) Class.forName(newBulletFullName).newInstance();
                    newBullet.init(
                            player,
                            getComponent(Movement.class).getXPrevious(),
                            getComponent(Movement.class).getYPrevious(),
                            random.nextInt(360),
                            0,
                            0,
                            configName
                    );

                    Global.location.objAdd(newBullet);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                Global.logger.println("Bullet create error: " + configName, Logger.Type.ERROR);
                Loader.exit();
            }
        }

        //Обработка столкновения родителя
        //Обязательно в конце, иначе сразу выйдет из метода, т.к. destroy = true
        super.collision(gameObject);
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

