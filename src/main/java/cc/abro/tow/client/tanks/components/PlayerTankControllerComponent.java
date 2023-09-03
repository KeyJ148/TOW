package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.collision.CollidableComponent;
import cc.abro.orchengine.gameobject.components.collision.CollisionType;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.gameobject.location.Border;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.tow.client.map.objects.Box;
import cc.abro.tow.client.map.objects.collised.CollisedMapObject;
import cc.abro.tow.client.map.objects.destroyed.DestroyedMapObject;
import cc.abro.tow.client.settings.SettingsService;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.stats.Stats;

import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerTankControllerComponent extends Component<Tank> implements Updatable {
    
    //Какие действия выполняются в текущий степ
    private boolean runUp = false;
    private boolean runDown = false;
    private boolean turnRight = false;
    private boolean turnLeft = false;

    //Для столкновений
    private boolean recoil = false;// в данынй момент танк отлетает от противника в рез. столкновения
    private long timer = 0; //таймер для отсёчта пройденного времени при столкновение
    private GameObject coll_gameObject = null; //Объекта с которым происходит столкновение

    //Т.к. поворот осуществляется не в классе Armor, то приходится дублировать функционал компонента Movement
    private double directionPrevious = 0;

    @Override
    public void update(long delta) {
        //Если мы мертвы, то не обрабатываем действия движения и прочего
        if (!getGameObject().isAlive()) return;

        Movement<Tank> movementComponent = getGameObject().getMovementComponent();
        Stats stats = getGameObject().getTankStatsComponent().getStats();

        /*
         * Выстрел
         */
        //Если нажата мышь и перезарядилась пушка и игрок вообще может стрелять
        /* TODO починить
        if (getGameObject().getLocation().getGuiLocationFrame().getMouse().isButtonDown(GLFW_MOUSE_BUTTON_1) && ((GunLoader) player.gun).nanoSecFromAttack <= 0 && player.stats.attackSpeed > 0) {
            ((GunLoader) player.gun).attack(); //Стреляем
        }*/

        /*
         * Определение направления движения
         */
        if (!recoil) {
            int vectorUp = 0, vectorRight = 0;
            if (getGameObject().getLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_W)) vectorUp++;
            if (getGameObject().getLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_S)) vectorUp--;
            if (getGameObject().getLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_D)) vectorRight++;
            if (getGameObject().getLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_A)) vectorRight--;

            runUp = false;
            runDown = false;
            turnRight = false;
            turnLeft = false;

            if (vectorUp == -1){
                runDown = true;
                if (Context.getService(SettingsService.class).getSettings().getControl().isTankHorizontalInverseControl()) {
                    vectorRight *= -1;
                }
            }
            if (vectorUp == 1) runUp = true;
            if (vectorRight == 1) turnRight = true;
            if (vectorRight == -1) turnLeft = true;

            //Движение корпуса танка
            movementComponent.speed = 0;
            if (runUp) movementComponent.speed = stats.speedUp;
            if (runDown) movementComponent.speed = -stats.speedDown;
        }


        /*
         * Действия, если недавно произошло столкновения с врагом и мы отлетаем от него
         */
        if (recoil) {
            timer += delta;
            if (timer > 300 * 1000 * 1000) {//300 Милисекунд
                recoil = false;
                turnRight = false;
                turnLeft = false;
                runUp = false;
                runDown = false;
                movementComponent.speed = 0.0;
                coll_gameObject = null;
            }
        }


        /*
         * Поворот корпуса танка
         */
        directionPrevious = movementComponent.getDirection();
        if (turnLeft || turnRight) {
            //Скорость поворота
            double deltaDirection = ((double) delta) / Math.pow(10, 9) * stats.speedRotateTank;

            //Если происходит отбрасывание назад, то скорость в 3 раза меньше
            if (recoil) deltaDirection = deltaDirection / 3;

            //Если поворачиваем на лево, то текущий угол + delta
            //Если поворачиваем на право, то текущий угол - delta
            if (turnRight) deltaDirection = -deltaDirection;

            //Текущий угол + delta (скорость поворота)
            movementComponent.setDirection(movementComponent.getDirection() + deltaDirection);
        }


        //TODO в отдельный компонент? Компонент по контролю корпуса и компонент по контролю пушки
        /*
         * Поворот дула пушки (много костылей)
         */
        /*
        Vector2<Double> relativePosition = player.gun.getRelativePosition();
        double relativeX = relativePosition.x + 0.1;
        double relativeY = relativePosition.y + 0.1;

        double pointDir = -Math.toDegrees(Math.atan((relativeY - getGameObject().getLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().y) / (relativeX - getGameObject().getLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().x)));

        double trunkUp = ((double) delta / 1000000000) * (player.stats.directionGunUp);
        if ((relativeX - getGameObject().getLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().x) > 0) {
            pointDir += 180;
        } else if ((relativeY - getGameObject().getLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().y) < 0) {
            pointDir += 360;
        }

        if ((pointDir - player.gun.getDirection()) > 0) {
            if ((pointDir - player.gun.getDirection()) > 180) {
                player.gun.setDirection(player.gun.getDirection() - trunkUp);
            } else {
                player.gun.setDirection(player.gun.getDirection() + trunkUp);
            }
        } else {
            if ((pointDir - player.gun.getDirection()) < -180) {
                player.gun.setDirection(player.gun.getDirection() + trunkUp);
            } else {
                player.gun.setDirection(player.gun.getDirection() - trunkUp);
            }
        }

        if ((Math.abs(pointDir - player.gun.getDirection()) < trunkUp * 1.5) ||
                (Math.abs(pointDir - player.gun.getDirection()) > 360 - trunkUp * 1.5)) {
            player.gun.setDirection(pointDir);
        }*/
    }
    
    public void collision(CollidableComponent collision, CollisionType collisionType) {
        if (collisionType == CollisionType.LEAVING) return;

        GameObject gameObject = collision.getGameObject();
        if (gameObject.getClass().equals(Box.class)) {
            Box box = (Box) gameObject;
            if (!box.isDestroyed()) {
                box.collisionPlayer(player);
            }
        }

        if (Set.of(Border.class, CollisedMapObject.class, DestroyedMapObject.class).contains(gameObject.getClass())) {
            player.armor.setX(player.getComponent(Movement.class).getXPrevious());
            player.armor.setY(player.getComponent(Movement.class).getYPrevious());
            player.getComponent(Movement.class).setDirection(directionPrevious);
        }

        if (gameObject.getClass().equals(DestroyedMapObject.class)) {
            DestroyedMapObject destroyedMapObject = (DestroyedMapObject) gameObject;
            if (destroyedMapObject.getStability() < player.stats.stability){
                Context.getService(TCPControl.class).send(22, String.valueOf(destroyedMapObject.getId()));
                destroyedMapObject.destroy();
            }
        }

        if (gameObject.getClass().equals(EnemyArmor.class)) {
            EnemyArmor enemyArmor = (EnemyArmor) gameObject;

            if ((!player.controller.recoil) || (!enemyArmor.equals(coll_gameObject))) {
                player.armor.setX(player.getComponent(Movement.class).getXPrevious());
                player.armor.setY(player.getComponent(Movement.class).getYPrevious());
                player.getComponent(Movement.class).setDirection(player.getComponent(Movement.class).getDirectionPrevious());
                recoil = true;
                timer = 0;
                coll_gameObject = enemyArmor;

                if (runUp) {
                    player.getComponent(Movement.class).speed = -player.getComponent(Movement.class).speed / 3;
                    runUp = false;
                    runDown = true;
                } else if (runDown) {
                    player.getComponent(Movement.class).speed = -player.getComponent(Movement.class).speed / 3;
                    runDown = false;
                    runUp = true;
                }

                if (turnLeft) {
                    turnLeft = false;
                    turnRight = true;
                } else if (turnRight) {
                    turnRight = false;
                    turnLeft = true;
                }
            }
        }
    }

    //TODO в отдельный компонент/сервис
    /*


    //Разрешения на подбор ящиков
    private boolean takeArmor = true;
    private boolean takeGun = true;
    private boolean takeBullet = true;
    private boolean takeHealth = true;

    //Перебираем все события нажатия клавиш
    List<KeyEvent<?>> keyboardEvents = getGameObject().getLocation().getGuiLocationFrame().getKeyboard().getEventHistory().getList();
        for (KeyEvent<?> event : keyboardEvents) {
        if (event.getAction() == GLFW_PRESS) {// Клавиша нажата
            switch (event.getKey()) {
                    //Клавиши запрета и разрешения на подбор ящиков
                    case GLFW_KEY_1 -> {
                        takeArmor = !takeArmor;
                        player.buttonsTake[0].getTextState().setText((player.takeArmor) ? "" : "x");
                    }
                    case GLFW_KEY_2 -> {
                        takeGun = !takeGun;
                        player.buttonsTake[1].getTextState().setText((player.takeGun) ? "" : "x");
                    }
                    case GLFW_KEY_3 -> {
                        takeBullet = !takeBullet;
                        player.buttonsTake[2].getTextState().setText((player.takeBullet) ? "" : "x");
                    }
                    case GLFW_KEY_4 -> {
                        takeHealth = !takeHealth;
                        player.buttonsTake[3].getTextState().setText((player.takeHealth) ? "" : "x");
                    }

                    //Вывод характеристик танка
                    case GLFW_KEY_F2 ->
                            Context.getService(ClientData.class).printStats = !Context.getService(ClientData.class).printStats;


                    //Вывод дебаг инфы
                    case GLFW_KEY_F3 ->
                            Context.getService(ClientData.class).printAnalyzerInfo = !Context.getService(ClientData.class).printAnalyzerInfo;


                    //Отрисовка масок //TODO разрешить только в локальной среде (профиль != PROD, профиль == DEV)
                    case GLFW_KEY_F4 -> Context.getService(GuiService.class).setMaskRendering(
                            !Context.getService(GuiService.class).isMaskRendering());


                    //Переключение камер после смерти //TODO в соответствующий сервис
                    case GLFW_KEY_LEFT -> cameraToNextEnemy();
                    case GLFW_KEY_RIGHT -> cameraToPrevEnemy();

                    //Клавиши для одиночной игры //TODO в отдельный сервис
                    //Переход на новую карту
                    case GLFW_KEY_N -> player.hp = -1000;


                    //Поднятие вампиризма до максимума
                    case GLFW_KEY_V -> {
                        if (Context.getService(ClientData.class).peopleMax == 1) player.vampire = 1;
                    }

                    //Имитация подбора ящика
                    //TODO переделать без создания ящика, а просто вызывая функцию в системе экипировки
                    case GLFW_KEY_T -> {
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.armor.getX(), player.armor.getY(), 0, -1);
                    }
                    case GLFW_KEY_G -> {
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.armor.getX(), player.armor.getY(), 1, -1);
                    }
                    case GLFW_KEY_B -> {
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.armor.getX(), player.armor.getY(), 2, -1);
                    }
                    case GLFW_KEY_H -> {
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.armor.getX(), player.armor.getY(), 3, -1);
                    }
                    case GLFW_KEY_F -> {
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.armor.getX(), player.armor.getY(), 4, -1);
                    }
                }
              }
          }
     */

    /* //TODO в отедельный сервис / сервисы

        if (getGameObject().getLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_ESCAPE)) Context.getService(Engine.class).stop();
        Context.getService(ClientData.class).showGameTabMenu = getGameObject().getLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_SPACE);
     */
}
