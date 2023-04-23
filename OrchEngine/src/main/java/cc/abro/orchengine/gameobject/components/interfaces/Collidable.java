package cc.abro.orchengine.gameobject.components.interfaces;

import cc.abro.orchengine.util.Vector2;

import java.util.List;
import java.util.Set;

public interface Collidable extends Positionable {
    void checkCollisions(Set<Collidable> collidables);
    List<Vector2<Double>> getAbsoluteMaskPoints();

    /*
     * UnsuitableObject-ом считается объект, который может быть больше чанка и поэтому его коллизии надо просчитывать
     * всегда, даже если он находиться на расстояние в несколько чанков от другого объекта
     */
    default boolean isUnsuitableCollidableObject() {
        return false;
    }
}
