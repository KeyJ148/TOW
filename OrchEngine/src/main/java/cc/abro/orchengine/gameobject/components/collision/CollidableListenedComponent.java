package cc.abro.orchengine.gameobject.components.collision;

import cc.abro.orchengine.gameobject.GameObject;

import java.util.HashSet;
import java.util.Set;

public abstract class CollidableListenedComponent extends CollidableComponent {

    private final Set<CollisionListener> collisionListeners = new HashSet<>(); //Список объектов которые нужно оповещать при коллизии

    public void addListener(CollisionListener listener) {
        this.collisionListeners.add(listener);
    }

    public void removeListener(CollisionListener listener) {
        this.collisionListeners.remove(listener);
    }

    protected void informListeners(GameObject gameObject) {
        for (CollisionListener listener : collisionListeners)
            if (listener != null) listener.collision(gameObject);
    }
}
