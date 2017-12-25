package game.client.person.player;

import engine.io.KeyboardHandler;
import engine.map.Border;
import engine.obj.Obj;
import engine.obj.components.Collision;
import game.client.ClientData;
import game.client.map.Box;
import game.client.map.Wall;
import game.client.person.enemy.EnemyArmor;
import org.lwjgl.input.Keyboard;

public class PlayerController implements Collision.CollisionListener{

    //Какие действия выполняеются в текущий степ
    public boolean turnRight = false;
    public boolean turnLeft = false;
    public boolean runUp = false;
    public boolean runDown = false;

    //Для столкновений
    private boolean controlMotion = true; //можно ли управлять танком
    private boolean recoil = false;// в данынй момент танк отлетает от противника в рез. столкновения
    private long timer = 0; //таймер для отсёчта пройденного времени при столкновение
    private Obj coll_obj = null; //Объекта с которым происходит столкновение

    //Т.к. поворот осуществляется не в классе Armor, то приходится дублировать функционал компонента Movement
    private double directionPrevious = 0;

    private Player player;

    public PlayerController(Player player){
        this.player = player;
    }

    public void update(long delta) {
        for (int i = 0; i < KeyboardHandler.bufferState.size(); i++) {
            if (KeyboardHandler.bufferState.get(i)) {//Клавиша нажата
                if (controlMotion) {
                    switch (KeyboardHandler.bufferKey.get(i)) {
                        case Keyboard.KEY_W:
                            player.armor.movement.speed = player.armor.speedTankUp;
                            runUp = true;
                            runDown = false;
                            break;
                        case Keyboard.KEY_A:
                            turnLeft = true;
                            turnRight = false;
                            break;
                        case Keyboard.KEY_D:
                            turnRight = true;
                            turnLeft = false;
                            break;
                        case Keyboard.KEY_S:
                            player.armor.movement.speed = player.armor.speedTankDown;
                            runDown = true;
                            runUp = false;
                            break;
                    }
                }

                //Клавиши запрета и разрешения на подбор ящиков
                switch (KeyboardHandler.bufferKey.get(i)) {
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
                }
            } else {//Клавиша отпущена
                if (controlMotion) {
                    switch (KeyboardHandler.bufferKey.get(i)) {
                        case Keyboard.KEY_W:
                            //Условие для ситации: W press; S press; W release;
                            if (player.armor.movement.speed > 0) player.armor.movement.speed = 0.0;
                            runUp = false;
                            break;
                        case Keyboard.KEY_A:
                            turnLeft = false;
                            break;
                        case Keyboard.KEY_D:
                            turnRight = false;
                            break;
                        case Keyboard.KEY_S:
                            //Условие для ситации: S press; W press; S release;
                            if (player.armor.movement.speed < 0) player.armor.movement.speed = 0.0;
                            runDown = false;
                            break;
                    }
                }
            }

            //Клавишы для одиночной игры
            if (ClientData.peopleMax == 1){
                //Переход на новую карту
                if (KeyboardHandler.isKeyDown(Keyboard.KEY_N)) ClientData.player.armor.hp = -1000;
                //Имитация подбора ящика
                if (KeyboardHandler.isKeyDown(Keyboard.KEY_T)) new Box(0, 0, 0, -1).collisionPlayer(player);
                if (KeyboardHandler.isKeyDown(Keyboard.KEY_G)) new Box(0, 0, 1, -1).collisionPlayer(player);
                if (KeyboardHandler.isKeyDown(Keyboard.KEY_B)) new Box(0, 0, 2, -1).collisionPlayer(player);
                if (KeyboardHandler.isKeyDown(Keyboard.KEY_H)) new Box(0, 0, 3, -1).collisionPlayer(player);
                if (KeyboardHandler.isKeyDown(Keyboard.KEY_F)) new Box(0, 0, 4, -1).collisionPlayer(player);
            }
        }

        //для столкновений
        if (recoil) {
            timer += delta;
            if (timer > 300 * 1000 * 1000) {//300 Милисекунд в наносекундах
                recoil = false;
                turnRight = false;
                turnLeft = false;
                runUp = false;
                runDown = false;
                player.armor.movement.speed = 0.0;
                controlMotion = true;
                coll_obj = null;
            }
        }

        //для поворота
        directionPrevious = player.armor.movement.getDirection();
        if (turnLeft || turnRight) {
            //Скорость поворота
            double deltaDirection = ((double) delta) / Math.pow(10, 9) * player.armor.directionTankUp;

            //Если происходит отбрасывание назад, то скорость в 3 раза меньше
            if (recoil) deltaDirection = deltaDirection / 3;

            //Если поворачиваем на лево, то текущий угол + delta
            //Если поворачиваем на право, то текущий угол - delta
            if (turnRight) deltaDirection = -deltaDirection;

            //Текущий угол + delta (скорость поворота)
            player.armor.movement.setDirection(player.armor.movement.getDirection() + deltaDirection);
        }
    }

    @Override
    public void collision(Obj obj) {
        if (obj.getClass().equals(Box.class)){
            Box box = (Box) obj;
            if (!box.isCollision){
                box.collisionPlayer(player);
            }
        }

        if (obj.getClass().equals(Wall.class) ||
                obj.getClass().equals(Border.class)){
            player.armor.position.x = player.armor.movement.getXPrevious();
            player.armor.position.y = player.armor.movement.getYPrevious();
            player.armor.movement.setDirection(directionPrevious);
        }

        if (obj.getClass().equals(EnemyArmor.class)){
            EnemyArmor enemyArmor = (EnemyArmor) obj;

            if ((!player.controller.recoil) || (!enemyArmor.equals(coll_obj))){
                player.armor.position.x = player.armor.movement.getXPrevious();
                player.armor.position.y = player.armor.movement.getYPrevious();
                player.armor.movement.setDirection(player.armor.movement.getDirectionPrevious());
                controlMotion = false;
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
