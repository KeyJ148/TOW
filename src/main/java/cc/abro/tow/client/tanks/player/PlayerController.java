package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.location.Border;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.map.objects.Box;
import cc.abro.tow.client.map.objects.collised.CollisedMapObject;
import cc.abro.tow.client.map.objects.destroyed.DestroyedMapObject;
import cc.abro.tow.client.tanks.enemy.Enemy;
import cc.abro.tow.client.tanks.enemy.EnemyArmor;
import org.liquidengine.legui.event.KeyEvent;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController extends GameObject implements Collision.CollisionListener {

    private static final boolean INVERSE_CONTROL = false;
    private final LocationManager locationManager;

    //Какие действия выполняеются в текущий степ
    public boolean runUp = false;
    public boolean runDown = false;
    public boolean turnRight = false;
    public boolean turnLeft = false;

    //Для столкновений
    private boolean recoil = false;// в данынй момент танк отлетает от противника в рез. столкновения
    private long timer = 0; //таймер для отсёчта пройденного времени при столкновение
    private GameObject coll_gameObject = null; //Объекта с которым происходит столкновение

    //Т.к. поворот осуществляется не в классе Armor, то приходится дублировать функционал компонента Movement
    private double directionPrevious = 0;

    private Player player;

    public PlayerController(Player player) {
        super(player.getLocation());
        this.player = player;
        locationManager = Context.getService(LocationManager.class);
    }

    @Override
    public void update(long delta) {
        /*
         * Смотрим на все зажатые клавиши
         */

        if (locationManager.getActiveLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_ESCAPE)) Context.getService(Engine.class).stop();
        Context.getService(ClientData.class).showGameTabMenu = locationManager.getActiveLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_SPACE);

        /*
         * Перебираем все события нажатия клавиш
         */
        List<KeyEvent<?>> keyboardEvents = locationManager.getActiveLocation().getGuiLocationFrame().getKeyboard().getEventHistory().getList();
        for (KeyEvent<?> event : keyboardEvents) {
            if (event.getAction() == GLFW_PRESS) {// Клавиша нажата

                switch (event.getKey()) {

                    //Клавиши запрета и разрешения на подбор ящиков
                    case GLFW_KEY_1:
                        player.takeArmor = !player.takeArmor;
                        player.buttonsTake[0].getTextState().setText((player.takeArmor) ? "" : "x");
                        break;
                    case GLFW_KEY_2:
                        player.takeGun = !player.takeGun;
                        player.buttonsTake[1].getTextState().setText((player.takeGun) ? "" : "x");
                        break;
                    case GLFW_KEY_3:
                        player.takeBullet = !player.takeBullet;
                        player.buttonsTake[2].getTextState().setText((player.takeBullet) ? "" : "x");
                        break;
                    case GLFW_KEY_4:
                        player.takeHealth = !player.takeHealth;
                        player.buttonsTake[3].getTextState().setText((player.takeHealth) ? "" : "x");
                        break;

                    //Вывод характеристик танка
                    case GLFW_KEY_F2:
                        Context.getService(ClientData.class).printStats = !Context.getService(ClientData.class).printStats;
                        break;

                    //Вывод дебаг инфы
                    case GLFW_KEY_F3:
                        Context.getService(ClientData.class).printAnalyzerInfo = !Context.getService(ClientData.class).printAnalyzerInfo;
                        break;

                    //Переключение камер после смерти
                    case GLFW_KEY_LEFT:
                        cameraToNextEnemy();
                        break;
                    case GLFW_KEY_RIGHT:
                        cameraToPrevEnemy();
                        break;

                    //Клавиши для одиночной игры
                    //Переход на новую карту
                    case GLFW_KEY_N:
                        player.hp = -1000;
                        break;

                    //Поднятие вампиризма до максимума
                    case GLFW_KEY_V:
                        if (Context.getService(ClientData.class).peopleMax == 1) player.vampire = 1;
                        break;

                    //Имитация подбора ящика
                    //TODO ящик генерируется в позиции player, которая не обновляется. В итоге звук не воспроизводится, т.к. камера далеко от player
                    case GLFW_KEY_T:
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.getX(), player.getY(), 0, -1).collisionPlayer(player);
                        break;
                    case GLFW_KEY_G:
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.getX(), player.getY(), 1, -1).collisionPlayer(player);
                        break;
                    case GLFW_KEY_B:
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.getX(), player.getY(), 2, -1).collisionPlayer(player);
                        break;
                    case GLFW_KEY_H:
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.getX(), player.getY(), 3, -1).collisionPlayer(player);
                        break;
                    case GLFW_KEY_F:
                        if (Context.getService(ClientData.class).peopleMax == 1)
                            new Box(getLocation(), player.getX(), player.getY(), 4, -1).collisionPlayer(player);
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
        if (locationManager.getActiveLocation().getGuiLocationFrame().getMouse().isButtonDown(GLFW_MOUSE_BUTTON_1) && ((Gun) player.gun).nanoSecFromAttack <= 0 && player.stats.attackSpeed > 0) {
            ((Gun) player.gun).attack(); //Стреляем
        }

        /*
         * Определение направления движения
         */
        if (!recoil) {
            int vectorUp = 0, vectorRight = 0;
            if (locationManager.getActiveLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_W)) vectorUp++;
            if (locationManager.getActiveLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_S)) vectorUp--;
            if (locationManager.getActiveLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_D)) vectorRight++;
            if (locationManager.getActiveLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_A)) vectorRight--;

            runUp = false;
            runDown = false;
            turnRight = false;
            turnLeft = false;

            if (vectorUp == -1){
                runDown = true;
                if (INVERSE_CONTROL) {
                    vectorRight *= -1;
                }
            }
            if (vectorUp == 1) runUp = true;
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
                coll_gameObject = null;
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
        Vector2<Double> relativePosition = player.gun.getRelativePosition();
        double relativeX = relativePosition.x + 0.1;
        double relativeY = relativePosition.y + 0.1;

        double pointDir = -Math.toDegrees(Math.atan((relativeY - locationManager.getActiveLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().y) / (relativeX - locationManager.getActiveLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().x)));

        double trunkUp = ((double) delta / 1000000000) * (player.stats.directionGunUp);
        if ((relativeX - locationManager.getActiveLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().x) > 0) {
            pointDir += 180;
        } else if ((relativeY - locationManager.getActiveLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().y) < 0) {
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
        }
    }

    @Override
    public void collision(GameObject gameObject) {
        if (gameObject.getClass().equals(Box.class)) {
            Box box = (Box) gameObject;
            if (!box.isDestroyed()) {
                box.collisionPlayer(player);
            }
        }

        if (Set.of(Border.class, CollisedMapObject.class, DestroyedMapObject.class).contains(gameObject.getClass())) {
            player.armor.setX(player.armor.getComponent(Movement.class).getXPrevious());
            player.armor.setY(player.armor.getComponent(Movement.class).getYPrevious());
            player.armor.getComponent(Movement.class).setDirection(directionPrevious);
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
                player.armor.setX(player.armor.getComponent(Movement.class).getXPrevious());
                player.armor.setY(player.armor.getComponent(Movement.class).getYPrevious());
                player.armor.getComponent(Movement.class).setDirection(player.armor.getComponent(Movement.class).getDirectionPrevious());
                recoil = true;
                timer = 0;
                coll_gameObject = enemyArmor;

                if (runUp) {
                    player.armor.getComponent(Movement.class).speed = -player.armor.getComponent(Movement.class).speed / 3;
                    runUp = false;
                    runDown = true;
                } else if (runDown) {
                    player.armor.getComponent(Movement.class).speed = -player.armor.getComponent(Movement.class).speed / 3;
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

    //TODO вынести в класс камеры?
    private void cameraToNextEnemy() {
        if (!locationManager.getActiveLocation().getCamera().hasFollowObject()){
            return;
        }
        List<Enemy> enemies = getEnemiesWithCamera();
        List<Enemy> enemiesDouble = Stream.concat(enemies.stream(), enemies.stream())
                .collect(Collectors.toList());
        int pos = getEnemyWithCameraPos(enemies);
        if (pos == -1){
            return;
        }

        for (int i = pos+1; i < enemiesDouble.size(); i++) {
            Enemy enemy = enemiesDouble.get(i);
            if (enemy.camera != null && enemy.alive) {
                locationManager.getActiveLocation().getCamera().setFollowObject(enemy.camera);
                break;
            }
        }
    }

    private void cameraToPrevEnemy() {
        if (!locationManager.getActiveLocation().getCamera().hasFollowObject()){
            return;
        }
        List<Enemy> enemies = getEnemiesWithCamera();
        List<Enemy> enemiesDouble = Stream.concat(enemies.stream(), enemies.stream())
                .collect(Collectors.toList());
        int pos = getEnemyWithCameraPos(enemies)+enemies.size();
        if (pos == -1){
            return;
        }

        for (int i = pos-1; i >= 0 ; i--) {
            Enemy enemy = enemiesDouble.get(i);
            if (enemy.camera != null && enemy.alive) {
                locationManager.getActiveLocation().getCamera().setFollowObject(enemy.camera);
                break;
            }
        }
    }

    private List<Enemy> getEnemiesWithCamera() {
        return Context.getService(ClientData.class).enemy.entrySet().stream()
                .filter(e -> e.getValue().alive)
                .filter(e -> e.getValue().camera != null)
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private int getEnemyWithCameraPos(List<Enemy> enemies) {
        int pos = -1;
        for (int i = 0; i < enemies.size(); i++) {
            if (locationManager.getActiveLocation().getCamera().getFollowObject().orElse(null) == enemies.get(i).camera) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}
