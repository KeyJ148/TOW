package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class PositionableComponent<T extends GameObject> extends Component<T> implements Positionable {

    private final Set<Consumer<Positionable>> listeners = new HashSet<>();
    @Getter @Setter
    private double relativeX, relativeY, relativeDirection;

    @Override
    public double getX() {
        double direction = Math.toRadians(getGameObject().getDirection());
        //Первый отступ "Вперёд"
        double deltaX = Math.cos(direction) * relativeX;
        //Второй отступ "В бок"
        double deltaX2 = Math.sin(direction) * relativeY; //Math.cos(direction-Math.PI/2) * point.y
        return getGameObject().getX() + deltaX + deltaX2;
    }

    public void setX(double x) {
        double direction = Math.toRadians(getGameObject().getDirection());
        double biasY = this.getY() - getGameObject().getY();
        double biasX = x - getGameObject().getX();
        this.relativeX = Math.cos(direction) * biasX - Math.sin(direction) * biasY;
        this.relativeY = Math.sin(direction) * biasX + Math.cos(direction) * biasY;
        notifyChangePositionListeners();
    }

    @Override
    public double getY() {
        double direction = Math.toRadians(getGameObject().getDirection());
        //Первый отступ "Вперёд"
        double deltaY = Math.sin(direction) * relativeX;
        //Второй отступ "В бок"
        double deltaY2 = -Math.cos(direction) * relativeY; //Math.sin(direction-Math.PI/2) * point.y
        return getGameObject().getY() - deltaY - deltaY2;
    }

    public void setY(double y) {
        double direction = Math.toRadians(getGameObject().getDirection());
        double biasY = y - getGameObject().getY();
        double biasX = this.getX() - getGameObject().getX();
        this.relativeX = Math.cos(direction) * biasX - Math.sin(direction) * biasY;
        this.relativeY = Math.sin(direction) * biasX + Math.cos(direction) * biasY;
        notifyChangePositionListeners();
    }

    public void setPosition(Vector2<Double> position) {
        setX(position.x);
        setY(position.y);
        notifyChangePositionListeners();
    }

    public void setRelativePosition(Vector2<Double> position) {
        setRelativeX(position.x);
        setRelativeY(position.y);
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
        listeners.remove(listener);
    }

    @Override
    public void notifyChangePositionListeners() {
        listeners.forEach(listener -> listener.accept(this));
    }
}
