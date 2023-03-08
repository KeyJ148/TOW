package cc.abro.orchengine.gameobject.components.collision;

import cc.abro.orchengine.gameobject.components.interfaces.Collidable;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.util.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Collision extends DrawableCollidableComponent {

    @Getter
    private final Mask mask; //Маска для текстуры этого объекта
    private final CollidableObjectType collidableObjectType;
    @Getter @Setter(AccessLevel.PRIVATE)
    private List<Vector2<Double>> absoluteMaskPoints; //Абсолютные координаты маски от левого верхнего угла карты

    public Collision(Mask mask, CollidableObjectType collidableObjectType) {
        this.mask = mask;
        this.collidableObjectType = collidableObjectType;
    }

    @Override
    public void initialize() {
        maskRecalculate();
    }

    @Override
    public void checkCollisions(Set<Collidable> collidables) {
        maskRecalculate();
        for (Collidable collidable : collidables) {
            Collision collisionComponent = (Collision) collidable;
            if (collisionComponent != this && checkCollision(collisionComponent)) { //TODO флаг, что сейчас с этим компонентом в столкновение. И проверять каждый раз после перемещения. А также оповещать другой компонент, что мы больше не пересекаемся, если переместились мы, или уничтожились (destroy)
                informListeners(collisionComponent);
                collisionComponent.informListeners(this);
            }
        }
    }

    @Override
    public CollidableObjectType getType() {
        return collidableObjectType;
    }

    //Перерасчёт маски относительно начала координат карты
    private void maskRecalculate() {
        List<Vector2<Double>> newAbsoluteMaskPoints = new ArrayList<>(mask.getCountPoints());

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

            newAbsoluteMaskPoints.add(new Vector2<>(
                    getGameObject().getX() + deltaX + deltaX2,
                    getGameObject().getY() - deltaY - deltaY2
            ));
        }

        setAbsoluteMaskPoints(newAbsoluteMaskPoints);
    }

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
        for (Vector2<Double> maskPoint : getAbsoluteMaskPoints()) {
            polygon.addPoint(maskPoint.x.intValue(), maskPoint.y.intValue());
        }
        return polygon;
    }
}
