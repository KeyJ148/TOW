package cc.abro.orchengine.gameobject.components.collision;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.PositionableComponent;
import cc.abro.orchengine.gameobject.components.interfaces.Collidable;

import java.util.*;

public abstract class CollidableComponent extends PositionableComponent<GameObject> implements Collidable {

    private final Map<CollidableObjectType, Set<CollisionListener>> collisionListeners = new HashMap<>(); //Список объектов которые нужно оповещать при коллизии с ключом мапы

    public CollidableComponent addListener(CollidableObjectType collidableObjectType, CollisionListener listener) {
        collisionListeners.computeIfAbsent(collidableObjectType, key -> new HashSet<>()).add(listener);
        return this;
    }

    public CollidableComponent removeListener(CollidableObjectType collidableObjectType, CollisionListener listener) {
        collisionListeners.getOrDefault(collidableObjectType, Collections.emptySet()).remove(listener);
        return this;
    }

    protected void informListeners(CollidableComponent otherCollidableComponent, CollisionType collisionType) {
        Set<CollisionListener> listeners = collisionListeners.getOrDefault(
                otherCollidableComponent.getType(),
                Collections.emptySet());

        for (CollisionListener listener : listeners ){
            listener.collision(otherCollidableComponent, collisionType);
        }
    }

    public abstract CollidableObjectType getType();
}
