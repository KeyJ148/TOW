package cc.abro.orchengine.gameobject.components.interfaces;

import cc.abro.orchengine.util.Vector2;

import java.util.function.Consumer;

public interface Positionable {
    double getX();
    double getY();

    void addChangePositionListener(Consumer<Positionable> listener);
    void removeChangePositionListener(Consumer<Positionable> listener);
    void notifyChangePositionListeners();

    default Vector2<Double> getPosition() {
        return new Vector2<>(getX(), getY());
    }
}
