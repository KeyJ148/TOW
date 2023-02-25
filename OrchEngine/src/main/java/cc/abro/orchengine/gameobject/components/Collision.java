package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.services.ServiceConsumer;
import cc.abro.orchengine.util.Vector2;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Collision extends cc.abro.orchengine.gameobject.components.PositionableComponent implements Updatable, Drawable, ServiceConsumer {
    public static final boolean MASK_DRAW = true; //TODO вынести в настройки через какой-нибудь сервис

    private final Mask mask;//Маска для текстуры этого объекта
    private Vector2<Double>[] maskAbsolute;//Абсолютные координаты маски от левого верхнего угла карты

    protected ArrayList<Class> collisionObjects = new ArrayList<>();//Список объектов с которыми надо проверять столкновения
    private final ArrayList<CollisionListener> listeners = new ArrayList<>();//Список объектов которых нужно оповещать при коллизии
    protected boolean calcInThisStep = false;//Расчитывалась ли маска уже в этом степе?

    public Collision(Mask mask) {
        this.mask = mask;
    }

    @Override
    public void initialize() {
        calc();
    }

    @Override
    public void update(long delta) {
        calcInThisStep = false;
        if (getGameObject().hasComponent(Movement.class)) calc();
        checkCollisionFromRoom();
    }

    @Override
    public void draw() {
        if (!MASK_DRAW) return;

        Vector2<Double>[] maskDrawView = new Vector2[maskAbsolute.length];
        for (int i = 0; i < maskDrawView.length; i++)
            maskDrawView[i] = getLocationManager().getActiveLocation().getCamera().toRelativePosition(maskAbsolute[i].copy());

        GL11.glLoadIdentity();
        Color.BLUE.bind();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (int i = 0; i < maskDrawView.length; i++) {
            GL11.glVertex2f(maskDrawView[i].x.floatValue(), maskDrawView[i].y.floatValue());
        }
        GL11.glEnd();
    }

    //Вызывает проверку столкновения с каждым объектом в локации
    public void checkCollisionFromRoom() {
        if (collisionObjects.size() == 0) return;

        for (GameObject objectFromRoom : getLocationManager().getActiveLocation().getObjects()) {//Цикл перебора всех объектов в локации
            if (objectFromRoom != null && objectFromRoom.hasComponent(Collision.class)) {//Если объект не был уничтожен и у него есть маска
                for (Class collisionObject : collisionObjects) { //Цикл перебора объектов, с которыми надо проверять столкновение
                    if ((objectFromRoom.getClass().equals(collisionObject)) //Если с эти объектом надо проверять столкновени
                            && (checkCollision(objectFromRoom))) { //И с этим объектом происходит столкновение
                        informListeners(objectFromRoom); //Информируем об этом всех слушателей
                    }
                }
            }
        }
    }

    //Проверка столкновения с объектом obj2
    public boolean checkCollision(GameObject gameObject2) {
        GameObject gameObject1 = getGameObject();
        Collision coll1 = gameObject1.getComponent(Collision.class);
        Collision coll2 = gameObject2.getComponent(Collision.class);

        if (coll1 == null || coll2 == null ||
                coll1.maskAbsolute == null || coll2.maskAbsolute == null) {
            return false;
        }

        //Проверка расстояния до объекта столкновения
        double gip1 = Math.sqrt(sqr(coll1.mask.getWidth()) + sqr(coll1.mask.getHeight())); //Гипотенуза объекта
        //Гипотинуза объекта, с которым сравниваем
        double gip2 = Math.sqrt(sqr(coll2.mask.getWidth()) + sqr(coll2.mask.getHeight()));
        //Расстояние от центра до центра
        double dis1To2 = Math.sqrt(sqr(gameObject1.getX() - gameObject2.getX()) +
                sqr(gameObject1.getY() - gameObject2.getY()));

        //Если до объекта слишком далеко, то столкновения нет
        if (dis1To2 > gip1 / 2 + gip2 / 2 + 30) {
            return false;
        }

        //Если объекты движимы и в этом степе ещё не спросчитывали маску
        if (gameObject1.hasComponent(Movement.class) && !coll1.calcInThisStep) coll1.calc();
        if (gameObject2.hasComponent(Movement.class) && !coll2.calcInThisStep) coll2.calc();

        //Просчёт столкновения
        //Создание полигонов
        Polygon p1 = new Polygon();
        for (int i = 0; i < coll1.maskAbsolute.length; i++) {
            p1.addPoint(coll1.maskAbsolute[i].x.intValue(), coll1.maskAbsolute[i].y.intValue());
        }

        Polygon p2 = new Polygon();
        for (int i = 0; i < coll2.maskAbsolute.length; i++) {
            p2.addPoint(coll2.maskAbsolute[i].x.intValue(), coll2.maskAbsolute[i].y.intValue());
        }

        //Проверка на коллизию полигонов
        for (int i = 0; i < p2.npoints; i++) {
            Point point = new Point(p2.xpoints[i], p2.ypoints[i]);
            if (p1.contains(point)) return true;
        }

        for (int i = 0; i < p1.npoints; i++) {
            Point point = new Point(p1.xpoints[i], p1.ypoints[i]);
            if (p2.contains(point)) return true;
        }

        return false;
    }

    //Перерасчёт маски относительно начала координат карты
    public void calc() {

        //Смещена начального угла с Востока на Север
        double direction = getGameObject().getDirection();
        direction = Math.toRadians(direction) - Math.PI / 2;

        //Просчёт маски
        this.maskAbsolute = new Vector2[mask.getMaskCenter().length];
        double cos = Math.cos(direction);
        double sin = Math.sin(direction);
        for (int i = 0; i < mask.getMaskCenter().length; i++) {
            double XDouble = cos * mask.getMaskCenter()[i].x;//Первый отступ
            double YDouble = sin * mask.getMaskCenter()[i].x;//"Вперёд"
            double XDouble2 = sin * mask.getMaskCenter()[i].y;//Второй отступ //Math.cos(direction-Math.PI/2) * ...
            double YDouble2 = -cos * mask.getMaskCenter()[i].y;//"В бок" //Math.sin(direction-Math.PI/2) * ...

            this.maskAbsolute[i] = new Vector2<>();
            this.maskAbsolute[i].x = getGameObject().getX() + XDouble + XDouble2;
            this.maskAbsolute[i].y = getGameObject().getY() - YDouble - YDouble2;
        }

        calcInThisStep = true;
    }

    public void addCollisionObjects(Class[] collisionObjects) {
        for (Class obj : collisionObjects)
            this.collisionObjects.add(obj);
    }

    public void deleteCollisionObject(Class obj) {
        this.collisionObjects.remove(obj);
    }

    public void cleanCollisionObjects() {
        this.collisionObjects = new ArrayList();
    }

    public Mask getMask() {
        return mask;
    }

    public static int sqr(int x) {
        return x * x;
    }

    public static double sqr(double x) {
        return x * x;
    }

    //Интерфейс реализуют все слушатели
    //При коллизи с объектом из списка коллизий вызывается метод collision
    public interface CollisionListener {
        void collision(GameObject gameObject);
    }

    public void addListener(CollisionListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(CollisionListener listener) {
        this.listeners.remove(listener);
    }

    protected void informListeners(GameObject gameObject) {
        for (CollisionListener listener : listeners)
            if (listener != null) listener.collision(gameObject);
    }

    @Override
    public List<Class<? extends Updatable>> getPreliminaryUpdateComponents() {
        return List.of(Movement.class, Follower.class);
    }

    @Override
    public int getZ() {
        return 5000;
    }
}
