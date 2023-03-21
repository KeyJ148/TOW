package cc.abro.orchengine.gameobject.location;

import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ChunkGrid<T extends Positionable> {

    @Getter
    private final int chunkSize;
    //В качестве ключа координаты чанка в сетке чанков
    private final Map<Vector2<Integer>, Chunk<T>> chunkByCoords = new HashMap<>();
    private final Map<T, Chunk<T>> chunkByObject = new HashMap<>();

    private final Consumer<Positionable> updateObjectPositionListener = this::updateObjectPosition;

    public ChunkGrid(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void add(T object) {
        Chunk<T> chunk = getOrCreateChunk(object);
        chunk.add(object);
        chunkByObject.put(object, chunk);
        object.addChangePositionListener(updateObjectPositionListener);
    }

    public void remove(T object) {
        getChunk(object).remove(object);
        chunkByObject.remove(object);
        object.removeChangePositionListener(updateObjectPositionListener);
    }

    //Проверка и при необходимости обновление объекта при перемещении из чанка в чанк
    private void updateObjectPosition(Positionable positionable) {
        T object = (T) positionable;
        Chunk<T> chunkLast = chunkByObject.get(object);
        Chunk<T> chunkNow = getOrCreateChunk(object.getPosition());

        if (chunkLast != chunkNow) {
            chunkLast.remove(object);
            chunkNow.add(object);
        }
    }

    protected Chunk<T> getChunk(T object) {
        return getChunk(object.getX(), object.getY());
    }

    //(x;y) -- координаты мировые (например, объекта), а не чанка в сетке чанков
    protected Chunk<T> getChunk(double x, double y) {
        return chunkByCoords.get(getChunkPosition(x, y));
    }

    //(x;y) -- координаты мировые (например, объекта), а не чанка в сетке чанков
    protected Chunk<T> getChunk(Vector2<Double> position) {
        return getChunk(position.x, position.y);
    }

    protected Chunk<T> getOrCreateChunk(T object) {
        return getOrCreateChunk(object.getX(), object.getY());
    }

    //(x;y) -- координаты мировые(например, объекта), а не чанка в сетке чанков
    protected Chunk<T> getOrCreateChunk(double x, double y) {
        return chunkByCoords.computeIfAbsent(getChunkPosition(x, y), cp -> new Chunk<>());
    }

    //(x;y) -- координаты мировые(например, объекта), а не чанка в сетке чанков
    protected Chunk<T> getOrCreateChunk(Vector2<Double> position) {
        return getOrCreateChunk(position.x, position.y);
    }

    //Получить координаты чанка в сетке чанков по мировым координатам
    protected Vector2<Integer> getChunkPosition(double x, double y) {
        return new Vector2<>(((int) x) / chunkSize, ((int) y) / chunkSize);
    }

    //(x;y) -- координаты чанка в сетке чанков
    protected Chunk<T> getChunkByChunkPosition(int x, int y) {
        return getChunkByChunkPosition(new Vector2<>(x, y));
    }

    //(x;y) -- координаты чанка в сетке чанков
    protected Chunk<T> getChunkByChunkPosition(Vector2<Integer> position) {
        return chunkByCoords.get(position);
    }

}
