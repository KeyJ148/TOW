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
import cc.abro.tow.client.tanks.enemy.EnemyTank;
import cc.abro.tow.client.tanks.equipment.BoxService;
import cc.abro.tow.client.tanks.stats.Stats;

import java.util.List;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerTankMovementControllerComponent extends Component<Tank> implements Updatable {
    
    //Какие действия выполняются в текущий степ
    private boolean runUp = false;
    private boolean runDown = false;
    private boolean turnRight = false;
    private boolean turnLeft = false;

    //Для столкновений
    private boolean recoil = false; //В данный момент танк отлетает от противника в результате столкновения
    private long timer = 0; //таймер для отсёчта пройденного времени при столкновение
    private GameObject collidableGameObject = null; //Объекта с которым происходит столкновение

    @Override
    public void update(long delta) {
        Stats stats = getGameObject().getTankStatsComponent().getStats();
        Movement<GameObject> movementComponent = getGameObject().getMovementComponent();

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
            movementComponent.setSpeed(0);
            if (runUp) movementComponent.setSpeed(stats.getSpeedUp());
            if (runDown) movementComponent.setSpeed(-stats.getSpeedDown());
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
                movementComponent.setSpeed(0);
                collidableGameObject = null;
            }
        }


        /*
         * Поворот корпуса танка
         */
        if (turnLeft || turnRight) {
            //Скорость поворота
            double deltaDirection = ((double) delta) / Math.pow(10, 9) * stats.getSpeedRotateTank();

            //Если происходит отбрасывание назад, то скорость в 3 раза меньше
            if (recoil) deltaDirection = deltaDirection / 3;

            //Если поворачиваем на лево, то текущий угол + delta
            //Если поворачиваем на право, то текущий угол - delta
            if (turnRight) deltaDirection = -deltaDirection;

            //Текущий угол + delta (скорость поворота)
            getGameObject().setDirection(getGameObject().getDirection() + deltaDirection);
        }
    }
    
    public void collision(CollidableComponent collision, CollisionType collisionType) {
        if (collisionType == CollisionType.LEAVING) return;

        Stats stats = getGameObject().getTankStatsComponent().getStats();
        Movement<GameObject> movementComponent = getGameObject().getMovementComponent();

        GameObject gameObject = collision.getGameObject();
        if (gameObject.getClass().equals(Box.class)) {
            Box box = (Box) gameObject;
            if (!box.isDestroyed()) {
                box.collisionWithPlayer();
                Context.getService(BoxService.class).takeBox(getGameObject(), box.getType());
            }
        }

        if (Set.of(Border.class, CollisedMapObject.class, DestroyedMapObject.class).contains(gameObject.getClass())) {
            getGameObject().setX(movementComponent.getXPrevious());
            getGameObject().setY(movementComponent.getYPrevious());
            getGameObject().setDirection(movementComponent.getDirectionPrevious());
        }

        if (gameObject.getClass().equals(DestroyedMapObject.class)) {
            DestroyedMapObject destroyedMapObject = (DestroyedMapObject) gameObject;
            if (destroyedMapObject.getStability() < stats.getStability()){
                Context.getService(TCPControl.class).send(22, String.valueOf(destroyedMapObject.getId()));
                destroyedMapObject.destroy();
            }
        }

        if (gameObject.getClass().equals(EnemyTank.class)) {
            EnemyTank enemyArmor = (EnemyTank) gameObject;

            if ((!recoil) || (!enemyArmor.equals(collidableGameObject))) {
                getGameObject().setX(movementComponent.getXPrevious());
                getGameObject().setY(movementComponent.getYPrevious());
                getGameObject().setDirection(movementComponent.getDirectionPrevious());
                recoil = true;
                timer = 0;
                collidableGameObject = enemyArmor;

                if (runUp) {
                    movementComponent.setSpeed(-movementComponent.getSpeed() / 3);
                    runUp = false;
                    runDown = true;
                } else if (runDown) {
                    movementComponent.setSpeed(-movementComponent.getSpeed() / 3);
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

    @Override
    public List<Class<? extends Updatable>> getPreliminaryUpdateComponents() {
        return List.of(Movement.class);
    }

    //TODO в отдельный компонент/сервис
    /*
    //Перебираем все события нажатия клавиш
    List<KeyEvent<?>> keyboardEvents = getGameObject().getLocation().getGuiLocationFrame().getKeyboard().getEventHistory().getList();
        for (KeyEvent<?> event : keyboardEvents) {
        if (event.getAction() == GLFW_PRESS) {// Клавиша нажата
            switch (event.getKey()) {


                    //Вывод дебаг инфы
                    case GLFW_KEY_F3 ->
                            Context.getService(ClientData.class).printAnalyzerInfo = !Context.getService(ClientData.class).printAnalyzerInfo; //TODO по идее у нас уже есть такой компонент для меню (вроде в MenuLocation)


                    //Отрисовка масок //TODO разрешить только в локальной среде (профиль != PROD, профиль == DEV)
                    case GLFW_KEY_F4 -> Context.getService(GuiService.class).setMaskRendering(
                            !Context.getService(GuiService.class).isMaskRendering());

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
