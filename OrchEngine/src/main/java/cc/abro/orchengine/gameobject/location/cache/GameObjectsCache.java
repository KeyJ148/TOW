package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.GameObject;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GameObjectsCache {

    private final Set<GameObject> gameObjects = new CopyOnWriteArraySet<>();

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void destroy() {
        gameObjects.forEach(GameObject::destroy);
    }

    public int getCountGameObjects() {
        return gameObjects.size();
    }

    @Deprecated
    public Set<GameObject> getObjects() {
        return Collections.unmodifiableSet(gameObjects);
    }
}
