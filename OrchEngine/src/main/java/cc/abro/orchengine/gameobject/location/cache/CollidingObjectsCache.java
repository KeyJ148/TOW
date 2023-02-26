package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.components.interfaces.Collidable;
import cc.abro.orchengine.gameobject.location.ChunkGrid;

public class CollidingObjectsCache extends ChunkGrid<Collidable> {

    public CollidingObjectsCache(int chunkSize) {
        super(chunkSize);
    }
}
