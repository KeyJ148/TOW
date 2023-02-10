package cc.abro.orchengine.gameobject.location;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.container.ListeningComponentsContainer.ComponentEvent;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.gameobject.location.cache.CollidingObjectsCache;
import cc.abro.orchengine.gameobject.location.cache.DrawableObjectsCache;
import cc.abro.orchengine.gameobject.location.cache.GameObjectsCache;
import cc.abro.orchengine.gameobject.location.cache.UpdatableObjectsCache;

import java.util.Map;
import java.util.Set;

public class ObjectsContainer {

    private final GameObjectsCache gameObjectsCache;
    private final UpdatableObjectsCache updatableObjectsCache;
    private final DrawableObjectsCache drawableObjectsCache;
    private final CollidingObjectsCache collidingObjectsCache;

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
        gameObject.addListener(this::componentGameObjectChanged);
    }

    public void remove(GameObject gameObject) {
        if (!gameObject.getAllComponents().isEmpty()) {
            throw new IllegalStateException("GameObject must have 0 components when it is removed from ObjectContainer");
        }
        gameObjectsCache.remove(gameObject);
        gameObject.removeListener(this::componentGameObjectChanged);
    }

    private void componentGameObjectChanged(Component component, ComponentEvent event) {
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
        //TODO instanceof collision
    }

    /* Прокси методы */
    public Set<GameObject> getObjects() {
        return gameObjectsCache.getObjects();
    }

    public void destroy() {
        gameObjectsCache.destroy();
    }

    public void update(long delta) {
        updatableObjectsCache.update(delta);
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

    //TODO методы ниже
    //Проверка и при необходимости обновление объекта при перемещении из чанка в чанк
    public void updateObjectPosition(GameObject gameObject) {
        /* TODO
        drawableObjectsCache.updateObjectPosition();
        collidingObjectsCache.updateObjectPosition();
        */
    }

    //TODO update и chunk больше никак не связаны, пофиксить значения
    public Statistic getStatistic() {
        return new Statistic(Map.of(), Map.of(), Map.of(), Map.of(), Map.of());
        /*return new Statistic(
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getChunksUpdated)),
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getObjectsUpdated)),
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getChunksRendered)),
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getObjectsRendered)),
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getUnsuitableObjectsRendered))
        );*/
    }

    public record Statistic(Map<Integer, Integer> chunksUpdatedByLayerZ,
                            Map<Integer, Integer> objectsUpdatedByLayerZ,
                            Map<Integer, Integer> chunksRenderedByLayerZ,
                            Map<Integer, Integer> objectsRenderedByLayerZ,
                            Map<Integer, Integer> unsuitableObjectsRenderedByLayerZ) {
    }
}
