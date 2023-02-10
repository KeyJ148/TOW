package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import lombok.Getter;
import lombok.Setter;

public class PositionableComponent extends Component implements Positionable {

    @Getter @Setter
    private double relativeX, relativeY;

    @Override
    public double getX() {
        return getGameObject().getX() + relativeX;
    }

    public void setX(double x) {
        this.relativeX = x - getGameObject().getX();
    }

    @Override
    public double getY() {
        return getGameObject().getY() + relativeY;
    }

    public void setY(double y) {
        this.relativeY = y - getGameObject().getY();
    }
}
