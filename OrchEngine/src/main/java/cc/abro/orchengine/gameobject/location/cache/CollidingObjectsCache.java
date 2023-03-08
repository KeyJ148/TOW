package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.components.interfaces.Collidable;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.gameobject.location.Chunk;
import cc.abro.orchengine.gameobject.location.ChunkGrid;
import cc.abro.orchengine.util.Vector2;

import java.util.HashSet;
import java.util.Set;

public class CollidingObjectsCache extends ChunkGrid<Collidable> {

    public CollidingObjectsCache(int chunkSize) {
        super(chunkSize);
    }

    @Override
    public void add(Collidable collidable) {
        super.add(collidable);
        collidable.addChangePositionListener(this::updatePosition);
    }

    @Override
    public void remove(Collidable collidable) {
        super.remove(collidable);
        collidable.removeChangePositionListener(this::updatePosition);
    }

    private void updatePosition(Positionable positionable) {
        Collidable collidable = (Collidable) positionable;
        Vector2<Integer> chunkPosition = getChunkPosition(collidable.getPosition().x, collidable.getPosition().y);
        Set<Collidable> collidableObjectsFromNearestChunks = new HashSet<>();
        for (int x = chunkPosition.x - 1; x <= chunkPosition.x + 1; x++) {
            for (int y = chunkPosition.y - 1; y <= chunkPosition.y + 1; y++) {
                Chunk<Collidable> chunk = getChunkByChunkPosition(x, y);
                if (chunk != null) {
                    collidableObjectsFromNearestChunks.addAll(chunk.getObjects());
                }
            }
        }

        collidable.checkCollisions(collidableObjectsFromNearestChunks);
    }
}
