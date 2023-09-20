package cc.abro.tow.client.tanks.equipment.bullet0;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.collision.CollidableComponent;
import cc.abro.orchengine.gameobject.components.collision.CollisionType;
import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.map.objects.collised.CollisedMapObject;
import cc.abro.tow.client.map.objects.destroyed.DestroyedMapObject;
import cc.abro.tow.client.tanks.enemy.EnemyTank;
import lombok.extern.log4j.Log4j2;

import java.util.Random;
import java.util.Set;

@Log4j2
public class BMass extends Bullet {

    public int minFragmentNumber;
    public int maxFragmentNumber;
    public String configName;

    @Override
    public void collision(CollidableComponent collision, CollisionType collisionType) {
        if (isDestroyed()) return;
        if (collisionType == CollisionType.LEAVING) return;
        GameObject gameObject = collision.getGameObject();

        if (Set.of(CollisedMapObject.class, DestroyedMapObject.class, EnemyTank.class).contains(gameObject.getClass())) {
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
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                log.fatal("Bullet create error: " + configName);
                throw new RuntimeException(e);
            }
        }

        //Обработка столкновения родителя
        //Обязательно в конце, иначе сразу выйдет из метода, т.к. destroy = true
        super.collision(collision, collisionType);
    }

    @Override
    public void loadData() {
        super.loadData();

        ConfigReader cr = new ConfigReader(getConfigFileName());
        minFragmentNumber = cr.findInteger("MIN_FRAGMENT_NUMBER");
        maxFragmentNumber = cr.findInteger("MAX_FRAGMENT_NUMBER");
        configName = cr.findString("CONFIG_NAME");
    }

}

