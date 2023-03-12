package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class PositionableComponent extends Component implements Positionable {

    private final Set<Consumer<Positionable>> listeners = new HashSet<>();
    @Getter @Setter
    private double relativeX, relativeY, relativeDirection;

    @Override
    public double getX() {
        //Первый отступ "Вперёд"
        double deltaX = Math.cos(getDirection()) * relativeX;
        //Второй отступ "В бок"
        double deltaX2 = Math.sin(getDirection()) * relativeY; //Math.cos(direction-Math.PI/2) * point.y
        return getGameObject().getX() + deltaX + deltaX2;
    }

    public void setX(double x) {
        this.relativeX = x - getGameObject().getX();
        notifyChangePositionListeners();
    }

    @Override
    public double getY() {
        //Первый отступ "Вперёд"
        double deltaY = Math.sin(getDirection()) * relativeX;
        //Второй отступ "В бок"
        double deltaY2 = -Math.cos(getDirection()) * relativeY; //Math.sin(direction-Math.PI/2) * point.y
        return getGameObject().getY() - deltaY - deltaY2;
    }

    public void setY(double y) {
        this.relativeY = y - getGameObject().getY();
        notifyChangePositionListeners();
    }

    public void setPosition(Vector2<Double> position) {
        relativeX = position.x;
        relativeY = position.y;
        notifyChangePositionListeners();
    }

    @Override
    public double getDirection() {
        return getGameObject().getDirection() + relativeDirection;
    }

    public void setDirection(double direction) {
        this.relativeDirection = direction - getGameObject().getDirection();
        notifyChangePositionListeners();
    }

    @Override
    public void addChangePositionListener(Consumer<Positionable> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangePositionListener(Consumer<Positionable> listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyChangePositionListeners() {
        listeners.forEach(listener -> listener.accept(this));
    }
}
