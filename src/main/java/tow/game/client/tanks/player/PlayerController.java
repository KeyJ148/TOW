package tow.game.client.tanks.player;

import tow.engine.Global;
import tow.engine.Loader;
import tow.engine.Vector2;
import tow.engine.io.KeyboardHandler;
import tow.engine.io.MouseHandler;
import tow.engine.map.Border;
import tow.engine.obj.Obj;
import tow.engine.obj.components.Collision;
import tow.game.client.ClientData;
import tow.game.client.inf.PlayerTable;
import tow.game.client.map.Box;
import tow.game.client.map.Wall;
import tow.game.client.tanks.enemy.EnemyArmor;
import org.lwjgl.input.Keyboard;

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
        super(0, 0, 0);
        this.player = player;
    }

    @Override
    public void update(long delta) {
        /*
         * Смотрим на все зажатые клавиши
         */

        if (KeyboardHandler.isKeyDown(Keyboard.KEY_ESCAPE)) Loader.exit();

        if (KeyboardHandler.isKeyDown(Keyboard.KEY_TAB) && !PlayerTable.enable) PlayerTable.enable();
        if (!KeyboardHandler.isKeyDown(Keyboard.KEY_TAB) && PlayerTable.enable) PlayerTable.disable();

        /*
         * Перебираем все события нажатия клавиш
         */
        for (int i = 0; i < KeyboardHandler.bufferState.size(); i++) {
            if (KeyboardHandler.bufferState.get(i)) {// Клавиша нажата

                switch (KeyboardHandler.bufferKey.get(i)) {

                    //Клавиши запрета и разрешения на подбор ящиков
                    case Keyboard.KEY_1:
                        player.takeArmor = !player.takeArmor;
                        break;
                    case Keyboard.KEY_2:
                        player.takeGun = !player.takeGun;
                        break;
                    case Keyboard.KEY_3:
                        player.takeBullet = !player.takeBullet;
                        break;
                    case Keyboard.KEY_4:
                        player.takeHealth = !player.takeHealth;
                        break;

                    //Вывод характеристик танка
                    case Keyboard.KEY_F3:
                        ClientData.printStats = !ClientData.printStats;
                        break;

                    //Клавиши для одиночной игры
                    //Переход на новую карту
                    case Keyboard.KEY_N:
                        if (ClientData.peopleMax == 1) player.hp = -1000;
                        break;

                    //Поднятие вампиризма до максимума
                    case Keyboard.KEY_V:
                        if (ClientData.peopleMax == 1) player.vampire = 1;
                        break;

                    //Имитация подбора ящика
                    case Keyboard.KEY_T:
                        if (ClientData.peopleMax == 1) new Box(player.position.x, player.position.y, 0, -1).collisionPlayer(player);
                        break;
                    case Keyboard.KEY_G:
                        if (ClientData.peopleMax == 1) new Box(player.position.x, player.position.y, 1, -1).collisionPlayer(player);
                        break;
                    case Keyboard.KEY_B:
                        if (ClientData.peopleMax == 1) new Box(player.position.x, player.position.y, 2, -1).collisionPlayer(player);
                        break;
                    case Keyboard.KEY_H:
                        if (ClientData.peopleMax == 1) new Box(player.position.x, player.position.y, 3, -1).collisionPlayer(player);
                        break;
                    case Keyboard.KEY_F:
                        if (ClientData.peopleMax == 1) new Box(player.position.x, player.position.y, 4, -1).collisionPlayer(player);
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
        if (MouseHandler.mouseDown1 && ((Gun) player.gun).nanoSecFromAttack <= 0 && player.stats.attackSpeed > 0){
            ((Gun) player.gun).attack(); //Стреляем
        }

        /*
         * Определение направления движения
         */
        if (!recoil) {
            int vectorUp = 0, vectorRight = 0;
            if (KeyboardHandler.isKeyDown(Keyboard.KEY_W)) vectorUp++;
            if (KeyboardHandler.isKeyDown(Keyboard.KEY_S)) vectorUp--;
            if (KeyboardHandler.isKeyDown(Keyboard.KEY_D)) vectorRight++;
            if (KeyboardHandler.isKeyDown(Keyboard.KEY_A)) vectorRight--;

            runUp = false;
            runDown = false;
            turnRight = false;
            turnLeft = false;
            if (vectorUp == 1) runUp = true;
            if (vectorUp == -1) runDown = true;
            if (vectorRight == 1) turnRight = true;
            if (vectorRight == -1) turnLeft = true;

            //Движение корпуса танка
            player.armor.movement.speed = 0;
            if (runUp) player.armor.movement.speed = player.stats.speedTankUp;
            if (runDown) player.armor.movement.speed = -player.stats.speedTankDown;
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
                player.armor.movement.speed = 0.0;
                coll_obj = null;
            }
        }


        /*
         * Поворот корпуса танка
         */
        directionPrevious = player.armor.movement.getDirection();
        if (turnLeft || turnRight) {
            //Скорость поворота
            double deltaDirection = ((double) delta) / Math.pow(10, 9) * player.stats.directionTankUp;

            //Если происходит отбрасывание назад, то скорость в 3 раза меньше
            if (recoil) deltaDirection = deltaDirection / 3;

            //Если поворачиваем на лево, то текущий угол + delta
            //Если поворачиваем на право, то текущий угол - delta
            if (turnRight) deltaDirection = -deltaDirection;

            //Текущий угол + delta (скорость поворота)
            player.armor.movement.setDirection(player.armor.movement.getDirection() + deltaDirection);
        }


        /*
         * Поворот дула пушки (много костылей)
         */
        Vector2<Integer> relativePosition = player.gun.position.getRelativePosition();
        double relativeX = relativePosition.x+0.1;
        double relativeY = relativePosition.y+0.1;

        double pointDir = -Math.toDegrees(Math.atan((relativeY-MouseHandler.mouseY)/(relativeX-MouseHandler.mouseX)));

        double trunkUp = ((double) delta/1000000000)*(player.stats.directionGunUp);
        if ((relativeX-MouseHandler.mouseX)>0){
            pointDir+=180;
        } else if ((relativeY-MouseHandler.mouseY)<0){
            pointDir+=360;
        }

        if ((pointDir - player.gun.position.getDirectionDraw()) > 0){
            if ((pointDir - player.gun.position.getDirectionDraw()) > 180){
                player.gun.position.setDirectionDraw(player.gun.position.getDirectionDraw() - trunkUp);
            } else {
                player.gun.position.setDirectionDraw(player.gun.position.getDirectionDraw() + trunkUp);
            }
        } else {
            if ((pointDir - player.gun.position.getDirectionDraw()) < -180){
                player.gun.position.setDirectionDraw(player.gun.position.getDirectionDraw() + trunkUp);
            } else {
                player.gun.position.setDirectionDraw(player.gun.position.getDirectionDraw() - trunkUp);
            }
        }

        if (    (Math.abs(pointDir - player.gun.position.getDirectionDraw()) < trunkUp*1.5) ||
                (Math.abs(pointDir - player.gun.position.getDirectionDraw()) > 360-trunkUp*1.5)){
            player.gun.position.setDirectionDraw(pointDir);
        }
    }

    @Override
    public void collision(Obj obj) {
        if (obj.getClass().equals(Box.class)){
            Box box = (Box) obj;
            if (!box.destroy){
                box.collisionPlayer(player);
            }
        }

        if (obj.getClass().equals(Border.class)){
            player.armor.position.x = player.armor.movement.getXPrevious();
            player.armor.position.y = player.armor.movement.getYPrevious();
            player.armor.movement.setDirection(directionPrevious);
        }

        if (obj.getClass().equals(Wall.class)){
            player.armor.position.x = player.armor.movement.getXPrevious();
            player.armor.position.y = player.armor.movement.getYPrevious();
            player.armor.movement.setDirection(directionPrevious);

            Wall wall = (Wall) obj;
            if (wall.stabillity < player.stats.stability){
                Global.tcpControl.send(22, String.valueOf(wall.mid));
                wall.destroyByArmor();
            }
        }

        if (obj.getClass().equals(EnemyArmor.class)){
            EnemyArmor enemyArmor = (EnemyArmor) obj;

            if ((!player.controller.recoil) || (!enemyArmor.equals(coll_obj))){
                player.armor.position.x = player.armor.movement.getXPrevious();
                player.armor.position.y = player.armor.movement.getYPrevious();
                player.armor.movement.setDirection(player.armor.movement.getDirectionPrevious());
                recoil = true;
                timer = 0;
                coll_obj = enemyArmor;

                if (runUp){
                    player.armor.movement.speed = -player.armor.movement.speed/3;
                    runUp = false;
                    runDown = true;
                } else if (runDown){
                    player.armor.movement.speed = -player.armor.movement.speed/3;
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
