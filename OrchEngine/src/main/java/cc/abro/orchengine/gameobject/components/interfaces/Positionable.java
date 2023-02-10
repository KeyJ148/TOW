package cc.abro.orchengine.gameobject.components.interfaces;

import cc.abro.orchengine.util.Vector2;

public interface Positionable {
    double getX();
    double getY();

    default Vector2<Double> getPosition() {
        return new Vector2<>(getX(), getY());
    }
}
