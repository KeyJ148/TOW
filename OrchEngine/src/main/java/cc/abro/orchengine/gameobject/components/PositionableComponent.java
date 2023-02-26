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
    private double relativeX, relativeY;

    @Override
    public double getX() {
        return getGameObject().getX() + relativeX;
    }

    public void setX(double x) {
        this.relativeX = x - getGameObject().getX();
        notifyChangePositionListeners();
    }

    @Override
    public double getY() {
        return getGameObject().getY() + relativeY;
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
