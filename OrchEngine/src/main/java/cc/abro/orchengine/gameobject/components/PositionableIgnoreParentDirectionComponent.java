package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import lombok.Setter;

public class PositionableIgnoreParentDirectionComponent<T extends GameObject> extends PositionableComponent<T> implements Positionable {
    @Getter @Setter
    private double relativeX, relativeY;
    private double direction;

    @Override
    public double getX() {
        return getGameObject().getX() + relativeX;
    }

    @Override
    public void setX(double x) {
        this.relativeX = x - getGameObject().getX();
        notifyChangePositionListeners();
    }

    @Override
    public double getY() {
        return getGameObject().getY() +relativeY;
    }

    @Override
    public void setY(double y) {
        this.relativeY = y - getGameObject().getY();
        notifyChangePositionListeners();
    }

    @Override
    public void setPosition(Vector2<Double> position) {
        setX(position.x);
        setY(position.y);
        notifyChangePositionListeners();
    }

    @Override
    public void setRelativePosition(Vector2<Double> position) {
        setRelativeX(position.x);
        setRelativeY(position.y);
        notifyChangePositionListeners();
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public void setDirection(double direction) {
        this.direction = direction;
        notifyChangePositionListeners();
    }
}
