package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.gameobject.location.Camera;
import cc.abro.orchengine.gameobject.location.Chunk;
import cc.abro.orchengine.gameobject.location.ObjectsContainer;
import lombok.Getter;

public class Location extends ObjectsContainer {

    @Getter
    private final int width, height;
    @Getter
    private final Camera camera; //Положение камеры в этой локации
    @Getter
    private final GuiLocationFrame guiLocationFrame;

    public Location(int width, int height) {
        this(width, height, Chunk.DEFAULT_SIZE);
    }

    public Location(int width, int height, int chunkSize) {
        this(width, height, chunkSize,
                new Camera(width / 2, height / 2), new GuiLocationFrame());
    }

    public Location(int width, int height, int chunkSize,
                    Camera camera, GuiLocationFrame guiLocationFrame) {
        super(chunkSize);
        this.width = width;
        this.height = height;

        this.camera = camera;
        this.guiLocationFrame = guiLocationFrame;
    }

    public void update(long delta) {
        super.update(delta);
        guiLocationFrame.update();
    }

    //Отрисовка части локации с размерами width и height вокруг камеры
    public void render(int width, int height) {
        super.render((int) camera.getX(), (int) camera.getY(), width, height);
        guiLocationFrame.render();
    }

    //Уничтожение локации и всех объектов в локации
    public void destroy() {
        super.destroy();
        guiLocationFrame.destroy();
    }
}
