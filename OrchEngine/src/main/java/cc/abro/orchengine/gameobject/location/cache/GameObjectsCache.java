package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.GameObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameObjectsCache {

    private final Set<GameObject> gameObjects = new HashSet<>();

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void destroy() {
        gameObjects.forEach(GameObject::destroy);
    }

    @Deprecated //TODO del and other deprecated functions Why is it deprecated
    public Set<GameObject> getObjects() {
        return Collections.unmodifiableSet(gameObjects);
    }
}
