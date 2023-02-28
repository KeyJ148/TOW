package cc.abro.orchengine.gameobject.location;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.container.ListeningComponentsContainer.ComponentEvent;
import cc.abro.orchengine.gameobject.components.interfaces.Collidable;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.gameobject.location.cache.CollidingObjectsCache;
import cc.abro.orchengine.gameobject.location.cache.DrawableObjectsCache;
import cc.abro.orchengine.gameobject.location.cache.GameObjectsCache;
import cc.abro.orchengine.gameobject.location.cache.UpdatableObjectsCache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjectsContainer {

    private final GameObjectsCache gameObjectsCache;
    private final UpdatableObjectsCache updatableObjectsCache;
    private final DrawableObjectsCache drawableObjectsCache;
    private final CollidingObjectsCache collidingObjectsCache;

    private final Set<GameObjectChangedEvent> gameObjectChangedEvents = new HashSet<>();

    public ObjectsContainer(int chunkSize) {
        gameObjectsCache = new GameObjectsCache();
        updatableObjectsCache = new UpdatableObjectsCache();
        collidingObjectsCache = new CollidingObjectsCache(chunkSize);
        drawableObjectsCache = new DrawableObjectsCache(chunkSize);
    }

    public void add(GameObject gameObject) {
        if (!gameObject.getAllComponents().isEmpty()) {
            throw new IllegalStateException("GameObject must have 0 components when it is added to ObjectContainer");
        }
        gameObjectsCache.add(gameObject);
        gameObject.addListener(this::saveComponentEvent);
    }

    public void remove(GameObject gameObject) {
        if (!gameObject.getAllComponents().isEmpty()) {
            throw new IllegalStateException("GameObject must have 0 components when it is removed from ObjectContainer");
        }
        gameObjectsCache.remove(gameObject);
        gameObject.removeListener(this::saveComponentEvent);
    }

    /* Прокси методы */
    public Set<GameObject> getObjects() {
        return gameObjectsCache.getObjects();
    }

    public void destroy() {
        gameObjectsCache.destroy();
    }

    public void update(long delta) {
        activateAllComponentChangedEvents();
        gameObjectChangedEvents.clear();

        gameObjectsCache.getObjects().forEach(g -> g.update(delta));
        updatableObjectsCache.update(delta);
        collidingObjectsCache.update(delta); //TODO а что если компонент collidable зависит от других компонентов? Или наоборот? Он должен вызываться в общем игровом цикле?
    }

    public void render(int x, int y,  int width, int height) {
        drawableObjectsCache.render(x, y, width, height);
    }

    public void runBeforeUpdateOnce(Runnable runnable) {
        updatableObjectsCache.runBeforeUpdateOnce(runnable);
    }

    public void runAfterUpdateOnce(Runnable runnable) {
        updatableObjectsCache.runAfterUpdateOnce(runnable);
    }

    public Statistic getStatistic() {
        DrawableObjectsCache.Statistic drawableStatistic = drawableObjectsCache.getStatistic();
        return new Statistic(
                drawableStatistic.chunksRenderedByLayerZ(),
                drawableStatistic.objectsRenderedByLayerZ(),
                drawableStatistic.unsuitableObjectsRenderedByLayerZ(),
                updatableObjectsCache.getCountUpdatableObjects(),
                gameObjectsCache.getCountGameObjects()
        );
    }

    public record Statistic(Map<Integer, Integer> chunksRenderedByLayerZ,
                            Map<Integer, Integer> objectsRenderedByLayerZ,
                            Map<Integer, Integer> unsuitableObjectsRenderedByLayerZ,
                            int countUpdatedObjects,
                            int countGameObjects) {
    }

    private record GameObjectChangedEvent(Component component, ComponentEvent event) {
    }

    private void saveComponentEvent(Component component, ComponentEvent event) {
        gameObjectChangedEvents.add(new GameObjectChangedEvent(component, event));
    }

    private void activateComponentEvent(Component component, ComponentEvent event) {
        if (component instanceof Updatable updatable) {
            switch (event) {
                case ADD -> updatableObjectsCache.add(updatable);
                case REMOVE -> updatableObjectsCache.remove(updatable);
            }
        }
        if (component instanceof Drawable drawable) {
            switch (event) {
                case ADD -> drawableObjectsCache.add(drawable);
                case REMOVE -> drawableObjectsCache.remove(drawable);
            }
        }
        if (component instanceof Collidable collidable) {
            switch (event) {
                case ADD -> collidingObjectsCache.add(collidable);
                case REMOVE -> collidingObjectsCache.remove(collidable);
            }
        }
    }

    private void activateAllComponentChangedEvents() {
        for (GameObjectChangedEvent gameObjectChangedEvent : gameObjectChangedEvents) {
            activateComponentEvent(gameObjectChangedEvent.component, gameObjectChangedEvent.event);
        }
    }
}
