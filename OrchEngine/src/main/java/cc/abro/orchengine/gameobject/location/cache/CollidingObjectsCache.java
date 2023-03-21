package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.components.interfaces.Collidable;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.gameobject.location.Chunk;
import cc.abro.orchengine.gameobject.location.ChunkGrid;
import cc.abro.orchengine.util.Vector2;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class CollidingObjectsCache extends ChunkGrid<Collidable> {

    private final Set<Positionable> updateObjectPositionEvents = new HashSet<>(); //TODO unsuitable объекты?

    private final Consumer<Positionable> updateObjectPositionListener = this::updateObjectPosition;

    public CollidingObjectsCache(int chunkSize) {
        super(chunkSize);
    }

    @Override
    public void add(Collidable collidable) {
        super.add(collidable);
        collidable.addChangePositionListener(updateObjectPositionListener);
    }

    @Override
    public void remove(Collidable collidable) {
        super.remove(collidable);
        collidable.removeChangePositionListener(updateObjectPositionListener);
    }

    public void update(long delta) {
        for (Positionable positionable : updateObjectPositionEvents) {
            updatePosition(positionable);
        }
        updateObjectPositionEvents.clear();
    }

    private void updateObjectPosition(Positionable positionable) {
        updateObjectPositionEvents.add(positionable);
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
