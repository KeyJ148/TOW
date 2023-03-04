package cc.abro.orchengine.gameobject.components.collision;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.services.GuiService;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Collision extends CollidableListenedComponent implements Drawable {

    private final GuiService guiService;

    @Getter
    private final Mask mask; //Маска для текстуры этого объекта
    private List<Vector2<Double>> absoluteMaskPoints; //Абсолютные координаты маски от левого верхнего угла карты

    public Collision(Mask mask) {
        this.mask = mask;
        guiService = Context.getService(GuiService.class);
    }

    @Override
    public void initialize() {
        maskRecalculate();
    }

    //Перерасчёт маски относительно начала координат карты
    @Override
    public void maskRecalculate() {
        absoluteMaskPoints = new ArrayList<>(mask.getCountPoints());

        //Смещена начального угла с Востока на Север
        double direction = getGameObject().getDirection();
        direction = Math.toRadians(direction) - Math.PI / 2;

        double cos = Math.cos(direction);
        double sin = Math.sin(direction);
        for (Vector2<Integer> point : mask.getPoints()) {
            //Первый отступ "Вперёд"
            double deltaX = cos * point.x;
            double deltaY = sin * point.x;
            //Второй отступ "В бок"
            double deltaX2 = sin * point.y; //Math.cos(direction-Math.PI/2) * point.y
            double deltaY2 = -cos * point.y; //Math.sin(direction-Math.PI/2) * point.y

            absoluteMaskPoints.add(new Vector2<>(
                    getGameObject().getX() + deltaX + deltaX2,
                    getGameObject().getY() - deltaY - deltaY2
            ));
        }
    }

    //Вызывает проверку столкновения с объектами в соседних чанках
    @Override //TODO проверять только в текущем и 8 соседних чанках (если это не unsuitable Collidable объект)
    public void checkCollisions() {
        /*
        for (GameObject objectFromRoom : getLocationManager().getActiveLocation().getObjects()) {//Цикл перебора всех объектов в локации
            if (objectFromRoom != null && objectFromRoom.hasComponent(Collision.class)) {//Если объект не был уничтожен и у него есть маска
                for (Class collisionObject : collisionObjects) { //Цикл перебора объектов, с которыми надо проверять столкновение
                    if ((objectFromRoom.getClass().equals(collisionObject)) //Если с эти объектом надо проверять столкновени
                            && (checkCollision(objectFromRoom))) { //И с этим объектом происходит столкновение
                        informListeners(objectFromRoom); //Информируем об этом всех слушателей
                    }
                }
            }
        }*/
    }

    @Override
    public void draw() {
        if (!guiService.isMaskRendering()) return;

        List<Vector2<Double>> maskDrawPoints = absoluteMaskPoints.stream()
                .map(getLocationManager().getActiveLocation().getCamera()::toRelativePosition)
                .toList();

        GL11.glLoadIdentity();
        Color.BLUE.bind();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (Vector2<Double> maskPoint : maskDrawPoints) {
            GL11.glVertex2f(maskPoint.x.floatValue(), maskPoint.y.floatValue());
        }
        GL11.glEnd();
    }

    @Override
    public int getZ() {
        return 5000;
    }

    //Not implemented methods because Z never changes
    @Override
    public void addChangeZListener(Consumer<Drawable> listener) {}
    @Override
    public void removeChangeZListener(Consumer<Drawable> listener) {}
    @Override
    public void notifyChangeZListeners() {}

    //Проверка столкновения с другим компонентом Collision
    private boolean checkCollision(Collision collision) {
        //Проверка на коллизию полигонов
        Polygon polygon1 = getAbsolutePolygon();
        Polygon polygon2 = collision.getAbsolutePolygon();

        if (!polygon1.getBounds().intersects(polygon2.getBounds())) {
            return false;
        }

        return checkPolygonContainPoints(polygon1, getPoints(polygon2)) ||
                checkPolygonContainPoints(polygon2, getPoints(polygon1));
    }

    private Set<Point> getPoints(Polygon polygon) {
        Set<Point> points = new HashSet<>(polygon.npoints);
        for (int i = 0; i < polygon.npoints; i++) {
            points.add(new Point(polygon.xpoints[i], polygon.ypoints[i]));
        }
        return points;
    }

    private boolean checkPolygonContainPoints(Polygon polygon, Set<Point> points) {
        for (Point point : points) {
            if (polygon.contains(point)) {
                return true;
            }
        }
        return false;
    }

    private Polygon getAbsolutePolygon() {
        Polygon polygon = new Polygon();
        for (Vector2<Double> maskPoint : absoluteMaskPoints) {
            polygon.addPoint(maskPoint.x.intValue(), maskPoint.y.intValue());
        }
        return polygon;
    }
}
