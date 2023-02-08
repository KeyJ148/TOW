package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import lombok.Getter;

import java.util.Map;
import java.util.TreeMap;

public class DrawableObjectsCache {

    @Getter
    private final int chunkSize;
    private final Map<Integer, DrawableLayer> layerByZ = new TreeMap<>();

    public DrawableObjectsCache(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void add(Drawable drawable) {
        int z = drawable.getZ();
        layerByZ.computeIfAbsent(z, u -> new DrawableLayer(z, chunkSize)).add(drawable);
    }

    public void remove(Drawable drawable) {
        int z = drawable.getZ();
        layerByZ.get(z).remove(drawable);
    }

    //Отрисовка локации с размерами width и height вокруг координат (x;y)
    public void render(int x, int y, int width, int height) {
        layerByZ.values().forEach(l -> l.render(x, y, width, height));
    }

    public void updateObjectPosition(Drawable drawable) {
        int z = drawable.getZ();
        layerByZ.get(z).updateObjectPosition(drawable);
    }
}
