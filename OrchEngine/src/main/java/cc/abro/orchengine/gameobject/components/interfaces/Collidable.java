package cc.abro.orchengine.gameobject.components.interfaces;

import java.util.Set;
import java.util.function.Consumer;

public interface Collidable extends Positionable {
    void maskRecalculate();
    void checkCollisions();

    void addChangeCollidableObjectsListener(Consumer<Collidable> listener);
    void removeChangeCollidableObjectsListener(Consumer<Collidable> listener);
    void notifyChangeCollidableObjectsListeners();

    Set<Class<? extends Collidable>> getCollidableObjects();
}
