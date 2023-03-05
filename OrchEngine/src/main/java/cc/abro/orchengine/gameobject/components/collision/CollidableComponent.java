package cc.abro.orchengine.gameobject.components.collision;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.PositionableComponent;
import cc.abro.orchengine.gameobject.components.interfaces.Collidable;

import java.util.*;

public abstract class CollidableComponent extends PositionableComponent implements Collidable {

    //TODO Проверять не по классу, а по другому значению, которое удобно наследовать, в идеале что-то связанное с Collidable/CollidableComponent
    private final Map<Class<? extends GameObject>, Set<CollisionListener>> collisionListeners = new HashMap<>(); //Список объектов которые нужно оповещать при коллизии с классом (ключ мапы)

    public void addListener(Class<? extends GameObject> collidableObject, CollisionListener listener) {
        collisionListeners.computeIfAbsent(collidableObject, key -> new HashSet<>()).add(listener);
    }

    public void removeListener(Class<? extends GameObject> collidableObject, CollisionListener listener) {
        collisionListeners.getOrDefault(collidableObject, Collections.emptySet()).remove(listener);
    }

    protected void informListeners(CollidableComponent collidableComponent) {
        Set<CollisionListener> listeners = collisionListeners.getOrDefault(collidableComponent.getGameObject().getClass(), Collections.emptySet());
        for (CollisionListener listener :listeners ){
            listener.collision(collidableComponent);
        }
    }
}
