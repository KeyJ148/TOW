package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.components.interfaces.Collidable;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.gameobject.location.ChunkGrid;

import java.util.HashSet;
import java.util.Set;

public class CollidingObjectsCache extends ChunkGrid<Collidable> {

    /**
     * Список Collidable объектов с не нулевым списком Collidable классов,
     * у которых надо проверять коллизии с исходным Collidable объектом
     */
    private final Set<Collidable> objectsWithNotNullCollisionSet = new HashSet<>();

    public CollidingObjectsCache(int chunkSize) {
        super(chunkSize);
    }

    @Override
    public void add(Collidable collidable) {
        super.add(collidable);
        collidable.addChangePositionListener(this::updatePosition);
        collidable.addChangeCollidableObjectsListener(this::changeCollidableObjectsInCollidableObject);

        if (!collidable.getCollidableObjects().isEmpty()) {
            objectsWithNotNullCollisionSet.add(collidable);
        }
    }

    @Override
    public void remove(Collidable collidable) {
        super.remove(collidable);
        collidable.removeChangePositionListener(this::updatePosition);
        collidable.removeChangeCollidableObjectsListener(this::changeCollidableObjectsInCollidableObject);

        objectsWithNotNullCollisionSet.remove(collidable);
    }

    public void update(long delta) {
        objectsWithNotNullCollisionSet.forEach(Collidable::checkCollisions);
    }

    private void updatePosition(Positionable positionable) {
        Collidable collidable = (Collidable) positionable;
        collidable.maskRecalculate();
    }

    private void changeCollidableObjectsInCollidableObject(Collidable collidable) {
        if (collidable.getCollidableObjects().isEmpty()) {
            objectsWithNotNullCollisionSet.remove(collidable);
        } else {
            objectsWithNotNullCollisionSet.add(collidable);
        }
    }
}
