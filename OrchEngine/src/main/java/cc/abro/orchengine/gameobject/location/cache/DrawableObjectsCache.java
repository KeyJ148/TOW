package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DrawableObjectsCache {

    @Getter
    private final int chunkSize;
    private final Map<Integer, DrawableLayer> layerByZ = new TreeMap<>();
    private final Map<Drawable, DrawableLayer> layerByObject = new HashMap<>();

    public DrawableObjectsCache(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void add(Drawable drawable) {
        int z = drawable.getZ();
        DrawableLayer layer = layerByZ.computeIfAbsent(z, u -> new DrawableLayer(z, chunkSize));
        layer.add(drawable);
        layerByObject.put(drawable, layer);
        drawable.addChangePositionListener(this::updateObjectPosition);
    }

    public void remove(Drawable drawable) {
        int z = drawable.getZ();
        layerByZ.get(z).remove(drawable);
        layerByObject.remove(drawable);
        drawable.removeChangePositionListener(this::updateObjectPosition);
    }

    //Отрисовка локации с размерами width и height вокруг координат (x;y)
    public void render(int x, int y, int width, int height) {
        layerByZ.values().forEach(l -> l.render(x, y, width, height));
    }

    //Проверка и при необходимости обновление объекта при перемещении из одного слоя в другой
    private void updateObjectPosition(Positionable positionable) {
        Drawable drawable = (Drawable) positionable;
        int z = drawable.getZ();
        DrawableLayer oldLayer = layerByObject.get(drawable);
        if (oldLayer.getZ() != z) {
            DrawableLayer newLayer = layerByZ.computeIfAbsent(z, u -> new DrawableLayer(z, chunkSize));
            oldLayer.remove(drawable);
            layerByObject.remove(drawable);
            newLayer.add(drawable);
            layerByObject.put(drawable, newLayer);
        }
    }

    public Statistic getStatistic() {
        return new Statistic(
                layerByZ.values().stream().collect(Collectors.toMap(DrawableLayer::getZ, DrawableLayer::getChunksRendered)),
                layerByZ.values().stream().collect(Collectors.toMap(DrawableLayer::getZ, DrawableLayer::getObjectsRendered)),
                layerByZ.values().stream().collect(Collectors.toMap(DrawableLayer::getZ, DrawableLayer::getUnsuitableObjectsRendered))
        );
    }

    public record Statistic(Map<Integer, Integer> chunksRenderedByLayerZ,
                            Map<Integer, Integer> objectsRenderedByLayerZ,
                            Map<Integer, Integer> unsuitableObjectsRenderedByLayerZ) {
    }
}
