package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.gameobject.location.Chunk;
import cc.abro.orchengine.gameobject.location.ChunkGrid;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class DrawableLayer extends ChunkGrid<Drawable> {

    @Getter
    private final int z;
    //Объекты, которые необходимо рендерить всегда (их текстура/маска/система частиц могут быть больше размера чанка)
    private final Set<Drawable> unsuitableObjects = new HashSet<>();

    @Getter
    private int chunksRendered = 0; //Кол-во отрисованных чанков
    @Getter
    private int objectsRendered = 0; //Кол-во отрисованных объектов
    @Getter
    private int unsuitableObjectsRendered = 0; //Кол-во отрисованных объектов, которые рендерятся вне системы чанков

    public DrawableLayer(int z, int chunkSize) {
        super(chunkSize);
        this.z = z;
    }

    @Override
    public void add(Drawable object) {
        if (!object.isUnsuitableDrawableObject()) {
            super.add(object);
        } else {
            unsuitableObjects.add(object);
        }
    }

    @Override
    public void remove(Drawable object) {
        if (!object.isUnsuitableDrawableObject()) {
            super.remove(object);
        } else {
            unsuitableObjects.remove(object);
        }
    }

    //Отрисовка чанков вокруг позиции x, y. Размер width, height + 1 чанк с каждой стороны
    public void render(int x, int y, int width, int height) {
        int renderChunksByAxisX = width / getChunkSize() + ((width % getChunkSize() == 0) ? 0 : 1);
        int renderChunksByAxisY = height / getChunkSize() + ((height % getChunkSize() == 0) ? 0 : 1);
        //Текстура объекта может выходить за границу чанка, поэтому чанк прилегающий к любой границе надо обрабатывать (+1)
        int renderSideChunksByAxisX = renderChunksByAxisX / 2 + 1;
        int renderSideChunksByAxisY = renderChunksByAxisY / 2 + 1;

        chunksRendered = 0;
        objectsRendered = 0;
        Vector2<Integer> renderChunkPos = getChunkPosition(x, y);
        for (int i = renderChunkPos.x - renderSideChunksByAxisX; i <= renderChunkPos.x + renderSideChunksByAxisX; i++) {
            for (int j = renderChunkPos.y - renderSideChunksByAxisY; j <= renderChunkPos.y + renderSideChunksByAxisY; j++) {
                Chunk<Drawable> renderingChunk = getChunkByChunkPosition(i, j);
                if (renderingChunk != null) {
                    renderingChunk.runForAll(Drawable::draw);
                    chunksRendered++;
                    objectsRendered += renderingChunk.objectsCount();
                }
            }
        }

        //Рендер объектов, которые могут быть больше чанка и рендерятся всегда
        for (Drawable unsuitableObject : unsuitableObjects) {
            unsuitableObject.draw();
        }
        unsuitableObjectsRendered = unsuitableObjects.size();
    }
}

