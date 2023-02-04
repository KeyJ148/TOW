package cc.abro.orchengine.gameobject.location;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class ObjectsContainer {

    @Getter
    private final int chunkSize;
    private final Map<Integer, Layer> layerByZ = new TreeMap<>();

    public ObjectsContainer(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void add(GameObject gameObject) {
        int z = gameObject.getZ();
        layerByZ.computeIfAbsent(z, u -> new Layer(z, chunkSize)).add(gameObject);
    }

    public void remove(GameObject gameObject) {
        int z = gameObject.getZ();
        layerByZ.get(z).remove(gameObject);
    }

    public void addUnsuitableObject(GameObject gameObject) {
        int z = gameObject.getZ();
        layerByZ.computeIfAbsent(z, u -> new Layer(z, chunkSize)).addUnsuitableObject(gameObject);
    }

    public void update(long delta) {
        //Делаем копию сета, иначе получаем ConcurrentModificationException,
        //т.к. во время апдейта можно создать новый объект и для этого будет создан новый слой
        new ArrayList<>(layerByZ.values()).forEach(layer -> layer.update(delta));
    }

    //Проверка и при необходимости обновление объекта при перемещении из чанка в чанк
    public void updateObjectPosition(GameObject gameObject, Vector2<Double> previousPosition) {
        int z = gameObject.getZ();
        layerByZ.computeIfAbsent(z, u -> new Layer(z, chunkSize))
                .updateObjectPosition(gameObject, previousPosition);
    }

    //Отрисовка локации с размерами width и height вокруг координат (x;y)
    public void render(int x, int y, int width, int height) {
        layerByZ.values().forEach(l -> l.render(x, y, width, height));
    }

    public void destroy() {
        layerByZ.values().forEach(Layer::destroy);
    }

    @Deprecated
    public Set<GameObject> getObjects() {
        Set<GameObject> allObjects = new HashSet<>();
        for (Layer layer : layerByZ.values()) {
            allObjects.addAll(layer.getObjects());
        }
        return allObjects;
    }

    public Statistic getStatistic() {
        return new Statistic(
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getChunksUpdated)),
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getObjectsUpdated)),
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getChunksRendered)),
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getObjectsRendered)),
                layerByZ.values().stream().collect(Collectors.toMap(Layer::getZ, Layer::getUnsuitableObjectsRendered))
        );
    }

    public record Statistic(Map<Integer, Integer> chunksUpdatedByLayerZ,
                            Map<Integer, Integer> objectsUpdatedByLayerZ,
                            Map<Integer, Integer> chunksRenderedByLayerZ,
                            Map<Integer, Integer> objectsRenderedByLayerZ,
                            Map<Integer, Integer> unsuitableObjectsRenderedByLayerZ) {
    }
}
