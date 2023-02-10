package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.components.Collision;

public class CollidingObjectsCache { //TODO extends ChunkGrid<Collision> (добавить реализацию этой системы в список задач при разработке системы коллизий?), сделать интерфейс Collidable

    private final int chunkSize;

    public CollidingObjectsCache(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void updateObjectPosition(Collision c) {

    }
}
