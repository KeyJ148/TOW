package tow.game.client.tanks.player;

import tow.engine.Global;
import tow.engine.Loader;
import tow.engine.Vector2;
import tow.engine.map.Border;
import tow.engine.obj.Obj;
import tow.engine.obj.components.Collision;
import tow.engine.obj.components.Movement;
import tow.engine.obj.components.Position;
import tow.game.client.ClientData;
import tow.game.client.map.Box;
import tow.game.client.map.Wall;
import tow.game.client.tanks.enemy.EnemyArmor;

import org.liquidengine.legui.event.KeyEvent;

import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController extends Obj implements Collision.CollisionListener{

    //Какие действия выполняеются в текущий степ
    public boolean runUp = false;
    public boolean runDown = false;
    public boolean turnRight = false;
    public boolean turnLeft = false;

    //Для столкновений
    private boolean recoil = false;// в данынй момент танк отлетает от противника в рез. столкновения
    private long timer = 0; //таймер для отсёчта пройденного времени при столкновение
    private Obj coll_obj = null; //Объекта с которым происходит столкновение

    //Т.к. поворот осуществляется не в классе Armor, то приходится дублировать функционал компонента Movement
    private double directionPrevious = 0;

    private Player player;

    public PlayerController(Player player){
        super(Arrays.asList(new Position(0,0,0)));
        this.player = player;
    }

    @Override
    public void update(long delta) {
        /*
         * Смотрим на все зажатые клавиши
         */

        //TODO: не всегда срабатывает
        if (Global.location.getKeyboard().isKeyDown(GLFW_KEY_ESCAPE)) Loader.exit();

        //TODO:
        //if (Global.location.getKeyboard().isKeyDown(GLFW_KEY_TAB) && !PlayerTable.enable) PlayerTable.enable();
        //if (!Global.location.getKeyboard().isKeyDown(GLFW_KEY_TAB) && PlayerTable.enable) PlayerTable.disable();

        /*
         * Перебираем все события нажатия клавиш
         */
        List<KeyEvent> keyboardEvents = Global.location.getKeyboard().getEventHistory().getList();
        for (KeyEvent event : keyboardEvents) {
            if (event.getAction() == GLFW_PRESS) {// Клавиша нажата

                switch (event.getKey()) {

                    //Клавиши запрета и разрешения на подбор ящиков
                    case GLFW_KEY_1:
                        player.takeArmor = !player.takeArmor;
                        break;
                    case GLFW_KEY_2:
                        player.takeGun = !player.takeGun;
                        break;
                    case GLFW_KEY_3:
                        player.takeBullet = !player.takeBullet;
                        break;
                    case GLFW_KEY_4:
                        player.takeHealth = !player.takeHealth;
                        break;

                    //Вывод характеристик танка
                    case GLFW_KEY_F3:
                        ClientData.printStats = !ClientData.printStats;
                        break;

                    //Клавиши для одиночной игры
                    //Переход на новую карту
                    case GLFW_KEY_N:
                        if (ClientData.peopleMax == 1) player.hp = -1000;
                        break;

                    //Поднятие вампиризма до максимума
                    case GLFW_KEY_V:
                        if (ClientData.peopleMax == 1) player.vampire = 1;
                        break;

                    //Имитация подбора ящика
                    case GLFW_KEY_T:
                        if (ClientData.peopleMax == 1) new Box(player.getComponent(Position.class).x, player.getComponent(Position.class).y, 0, -1).collisionPlayer(player);
                        break;
                    case GLFW_KEY_G:
                        if (ClientData.peopleMax == 1) new Box(player.getComponent(Position.class).x, player.getComponent(Position.class).y, 1, -1).collisionPlayer(player);
                        break;
                    case GLFW_KEY_B:
                        if (ClientData.peopleMax == 1) new Box(player.getComponent(Position.class).x, player.getComponent(Position.class).y, 2, -1).collisionPlayer(player);
                        break;
                    case GLFW_KEY_H:
                        if (ClientData.peopleMax == 1) new Box(player.getComponent(Position.class).x, player.getComponent(Position.class).y, 3, -1).collisionPlayer(player);
                        break;
                    case GLFW_KEY_F:
                        if (ClientData.peopleMax == 1) new Box(player.getComponent(Position.class).x, player.getComponent(Position.class).y, 4, -1).collisionPlayer(player);
                        break;
                }
            }
        }


        //Если мы мертвы, то не обрабатываем действия движения и прочего
        if (!player.alive) return;

        /*
         * Выстрел
         */
        //Если нажата мышь и перезарядилась пушка и игрок вообще может стрелять
        if (Global.location.getMouse().isButtonDown(GLFW_MOUSE_BUTTON_1) && ((Gun) player.gun).nanoSecFromAttack <= 0 && player.stats.attackSpeed > 0){
            ((Gun) player.gun).attack(); //Стреляем
        }

        /*
         * Определение направления движения
         */
        if (!recoil) {
            int vectorUp = 0, vectorRight = 0;
            if (Global.location.getKeyboard().isKeyDown(GLFW_KEY_W)) vectorUp++;
            if (Global.location.getKeyboard().isKeyDown(GLFW_KEY_S)) vectorUp--;
            if (Global.location.getKeyboard().isKeyDown(GLFW_KEY_D)) vectorRight++;
            if (Global.location.getKeyboard().isKeyDown(GLFW_KEY_A)) vectorRight--;

            runUp = false;
            runDown = false;
            turnRight = false;
            turnLeft = false;
            if (vectorUp == 1) runUp = true;
            if (vectorUp == -1) runDown = true;
            if (vectorRight == 1) turnRight = true;
            if (vectorRight == -1) turnLeft = true;

            //Движение корпуса танка
            player.armor.getComponent(Movement.class).speed = 0;
            if (runUp) player.armor.getComponent(Movement.class).speed = player.stats.speedTankUp;
            if (runDown) player.armor.getComponent(Movement.class).speed = -player.stats.speedTankDown;
        }


        /*
         * Действия, если недавно произошло столкновения с врагом и мы отлетаем от него
         */
        if (recoil) {
            timer += delta;
            if (timer > 300 * 1000 * 1000) {//300 Милисекунд в наносекундах
                recoil = false;
                turnRight = false;
                turnLeft = false;
                runUp = false;
                runDown = false;
                player.armor.getComponent(Movement.class).speed = 0.0;
                coll_obj = null;
            }
        }


        /*
         * Поворот корпуса танка
         */
        directionPrevious = player.armor.getComponent(Movement.class).getDirection();
        if (turnLeft || turnRight) {
            //Скорость поворота
            double deltaDirection = ((double) delta) / Math.pow(10, 9) * player.stats.directionTankUp;

            //Если происходит отбрасывание назад, то скорость в 3 раза меньше
            if (recoil) deltaDirection = deltaDirection / 3;

            //Если поворачиваем на лево, то текущий угол + delta
            //Если поворачиваем на право, то текущий угол - delta
            if (turnRight) deltaDirection = -deltaDirection;

            //Текущий угол + delta (скорость поворота)
            player.armor.getComponent(Movement.class).setDirection(player.armor.getComponent(Movement.class).getDirection() + deltaDirection);
        }


        /*
         * Поворот дула пушки (много костылей)
         */
        Vector2<Integer> relativePosition = player.gun.getComponent(Position.class).getRelativePosition();
        double relativeX = relativePosition.x+0.1;
        double relativeY = relativePosition.y+0.1;

        double pointDir = -Math.toDegrees(Math.atan((relativeY-Global.location.getMouse().getCursor().getPosition().y)/(relativeX-Global.location.getMouse().getCursor().getPosition().x)));

        double trunkUp = ((double) delta/1000000000)*(player.stats.directionGunUp);
        if ((relativeX-Global.location.getMouse().getCursor().getPosition().x)>0){
            pointDir+=180;
        } else if ((relativeY-Global.location.getMouse().getCursor().getPosition().y)<0){
            pointDir+=360;
        }

        if ((pointDir - player.gun.getComponent(Position.class).getDirectionDraw()) > 0){
            if ((pointDir - player.gun.getComponent(Position.class).getDirectionDraw()) > 180){
                player.gun.getComponent(Position.class).setDirectionDraw(player.gun.getComponent(Position.class).getDirectionDraw() - trunkUp);
            } else {
                player.gun.getComponent(Position.class).setDirectionDraw(player.gun.getComponent(Position.class).getDirectionDraw() + trunkUp);
            }
        } else {
            if ((pointDir - player.gun.getComponent(Position.class).getDirectionDraw()) < -180){
                player.gun.getComponent(Position.class).setDirectionDraw(player.gun.getComponent(Position.class).getDirectionDraw() + trunkUp);
            } else {
                player.gun.getComponent(Position.class).setDirectionDraw(player.gun.getComponent(Position.class).getDirectionDraw() - trunkUp);
            }
        }

        if (    (Math.abs(pointDir - player.gun.getComponent(Position.class).getDirectionDraw()) < trunkUp*1.5) ||
                (Math.abs(pointDir - player.gun.getComponent(Position.class).getDirectionDraw()) > 360-trunkUp*1.5)){
            player.gun.getComponent(Position.class).setDirectionDraw(pointDir);
        }
    }

    @Override
    public void collision(Obj obj) {
        if (obj.getClass().equals(Box.class)){
            Box box = (Box) obj;
            if (!box.isDestroy()){
                box.collisionPlayer(player);
            }
        }

        if (obj.getClass().equals(Border.class)){
            player.armor.getComponent(Position.class).x = player.armor.getComponent(Movement.class).getXPrevious();
            player.armor.getComponent(Position.class).y = player.armor.getComponent(Movement.class).getYPrevious();
            player.armor.getComponent(Movement.class).setDirection(directionPrevious);
        }

        if (obj.getClass().equals(Wall.class)){
            player.armor.getComponent(Position.class).x = player.armor.getComponent(Movement.class).getXPrevious();
            player.armor.getComponent(Position.class).y = player.armor.getComponent(Movement.class).getYPrevious();
            player.armor.getComponent(Movement.class).setDirection(directionPrevious);

            Wall wall = (Wall) obj;
            if (wall.stabillity < player.stats.stability){
                Global.tcpControl.send(22, String.valueOf(wall.mid));
                wall.destroyByArmor();
            }
        }

        if (obj.getClass().equals(EnemyArmor.class)){
            EnemyArmor enemyArmor = (EnemyArmor) obj;

            if ((!player.controller.recoil) || (!enemyArmor.equals(coll_obj))){
                player.armor.getComponent(Position.class).x = player.armor.getComponent(Movement.class).getXPrevious();
                player.armor.getComponent(Position.class).y = player.armor.getComponent(Movement.class).getYPrevious();
                player.armor.getComponent(Movement.class).setDirection(player.armor.getComponent(Movement.class).getDirectionPrevious());
                recoil = true;
                timer = 0;
                coll_obj = enemyArmor;

                if (runUp){
                    player.armor.getComponent(Movement.class).speed = -player.armor.getComponent(Movement.class).speed/3;
                    runUp = false;
                    runDown = true;
                } else if (runDown){
                    player.armor.getComponent(Movement.class).speed = -player.armor.getComponent(Movement.class).speed/3;
                    runDown = false;
                    runUp = true;
                }

                if (turnLeft){
                    turnLeft = false;
                    turnRight = true;
                } else if (turnRight){
                    turnRight = false;
                    turnLeft = true;
                }
            }
        }
    }
}
