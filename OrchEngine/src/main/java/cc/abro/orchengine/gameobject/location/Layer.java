package cc.abro.orchengine.gameobject.location;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;

import java.util.*;

public class Layer {

    @Getter
    private final int z;
    private final int chunkSize;
    //В качестве ключа координаты чанка в сетке чанков
    private final Map<Vector2<Integer>, Chunk> chunkByCoords = new HashMap<>();
    //Объекты, которые необходимо рендерить всегда (их текстура или маска могут быть больше размера чанка)
    private final Set<GameObject> unsuitableObjects = new HashSet<>();

    @Getter
    private int chunksUpdated = 0; //Кол-во обновленных чанков
    @Getter
    private int chunksRendered = 0; //Кол-во отрисованных чанков
    @Getter
    private int objectsUpdated = 0; //Кол-во обновленных объектов
    @Getter
    private int objectsRendered = 0; //Кол-во отрисованных объектов
    @Getter
    private int unsuitableObjectsRendered = 0; //Кол-во отрисованных объектов, которые рендерятся вне системы чанков

    public Layer(int z, int chunkSize) {
        this.z = z;
        this.chunkSize = chunkSize;
    }

    public void add(GameObject gameObject) {
        getOrCreateChunk(gameObject).add(gameObject);
    }

    public void remove(GameObject gameObject) {
        getChunk(gameObject).remove(gameObject);
        unsuitableObjects.remove(gameObject);
    }

    public void addUnsuitableObject(GameObject gameObject) {
        unsuitableObjects.add(gameObject);
    }

    public void checkGameObjectChunkChanged(GameObject gameObject) {
        if (!gameObject.hasComponent(Movement.class) || unsuitableObjects.contains(gameObject)) return;

        Chunk chunkNow = getOrCreateChunk((int) gameObject.getX(), (int) gameObject.getY());
        Chunk chunkLast = getChunk((int) gameObject.getComponent(Movement.class).getXPrevious(), (int) gameObject.getComponent(Movement.class).getYPrevious());

        if (chunkLast != chunkNow) {
            if (chunkLast != null) chunkLast.remove(gameObject); //TODO del if (chunkLast != null)
            chunkNow.add(gameObject);
        }
    }

    public void update(long delta) {
        //Делаем копию сета, иначе получаем ConcurrentModificationException,
        //т.к. во время апдейта можно создать новый объект и для этого будет создан новый чанк
        Set<Chunk> updatedChunks = new HashSet<>();
        updatedChunks.addAll(chunkByCoords.values());

        //TODO ниже
        //TODO Сейчас пробегаем по всем чанкам и обновляем все объекты. Но большинство объектов статичны и не обновляемы. Сделать интерфейс Updatable?
        chunksUpdated = 0;
        objectsUpdated = 0;
        updatedChunks.forEach(chunk -> {
            chunk.update(delta);
            chunksUpdated++;
            objectsUpdated += chunk.size();
        });
    }

    //Отрисовка чанков вокруг позиции x, y. Размер width, height + 1 чанк с каждой стороны
    public void render(int x, int y, int width, int height) {
        int renderChunksByAxisX = width / chunkSize + ((width % chunkSize == 0) ? 0 : 1);
        int renderChunksByAxisY = height / chunkSize + ((height % chunkSize == 0) ? 0 : 1);
        //Текстура объекта может выходить за границу чанка, поэтому чанк прилегающий к любой границе надо обрабатывать (+1)
        int renderSideChunksByAxisX = renderChunksByAxisX / 2 + 1;
        int renderSideChunksByAxisY = renderChunksByAxisY / 2 + 1;

        chunksRendered = 0;
        objectsRendered = 0;
        Vector2<Integer> renderChunkPos = getChunkPosition(x, y);
        for (int i = renderChunkPos.x - renderSideChunksByAxisX; i <= renderChunkPos.x + renderSideChunksByAxisX; i++) {
            for (int j = renderChunkPos.y - renderSideChunksByAxisY; j <= renderChunkPos.y + renderSideChunksByAxisY; j++) {
                Chunk renderingChunk = chunkByCoords.get(new Vector2<>(i, j));
                if (renderingChunk != null) {
                    renderingChunk.render();
                    chunksRendered++;
                    objectsRendered += renderingChunk.size();
                }
            }
        }

        //Рендер объектов, которые могут быть больше чанка и рендерятся всегда
        unsuitableObjectsRendered = 0;
        for (GameObject unsuitableGameObject : unsuitableObjects) {
            unsuitableGameObject.draw();
            unsuitableObjectsRendered++;
        }
    }

    @Deprecated
    public List<GameObject> getObjects() {
        List<GameObject> allObjects = new ArrayList<>();
        for (Chunk chunk : chunkByCoords.values()) {
            allObjects.addAll(chunk.getObjects());
        }
        return allObjects;
    }

    public void destroy() {
        chunkByCoords.values().forEach(Chunk::destroy);
    }

    private Chunk getChunk(GameObject gameObject) {
        return getChunk(
                (int) gameObject.getX(),
                (int) gameObject.getY());
    }

    //(x;y) -- координаты мировые (например, объекта), а не чанка в сетке чанков
    private Chunk getChunk(int x, int y) {
        return chunkByCoords.get(getChunkPosition(x, y));
    }

    private Chunk getOrCreateChunk(GameObject gameObject) {
        return getOrCreateChunk(
                (int) gameObject.getX(),
                (int) gameObject.getY());
    }

    //(x;y) -- координаты мировые(например, объекта), а не чанка в сетке чанков
    private Chunk getOrCreateChunk(int x, int y) {
        return chunkByCoords.computeIfAbsent(getChunkPosition(x, y), cp -> new Chunk());
    }

    //Получить координаты чанка в сетке чанков по мировым координатам
    private Vector2<Integer> getChunkPosition(int x, int y) {
        return new Vector2<>(x / chunkSize, y / chunkSize);
    }
}

