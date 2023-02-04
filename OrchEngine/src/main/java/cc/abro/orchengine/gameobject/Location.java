package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.gameobject.location.Background;
import cc.abro.orchengine.gameobject.location.Camera;
import cc.abro.orchengine.gameobject.location.Chunk;
import cc.abro.orchengine.gameobject.location.ObjectsContainer;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class Location extends ObjectsContainer {

    @Getter
    private final int width, height;
    @Getter
    private final Camera camera; //Положение камеры в этой локации

    @Getter
    private final GuiLocationFrame guiLocationFrame;
    @Getter
    @Setter
    private Background background; //Фон локации (цвет и текстура)

    public Location() {
        this(Integer.MAX_VALUE, Integer.MAX_VALUE, Chunk.DEFAULT_SIZE);
    }

    public Location(int chunkSize) {
        this(Integer.MAX_VALUE, Integer.MAX_VALUE, chunkSize);
    }

    public Location(int width, int height) {
        this(width, height, Chunk.DEFAULT_SIZE);
    }

    public Location(int width, int height, int chunkSize) {
        this(width, height, chunkSize,
                new Camera(width / 2, height / 2), new GuiLocationFrame(), new Background());
    }

    public Location(int width, int height, int chunkSize,
                    Camera camera, GuiLocationFrame guiLocationFrame, Background background) {
        super(chunkSize);
        this.width = width;
        this.height = height;

        this.camera = camera;
        this.guiLocationFrame = guiLocationFrame;
        this.background = background;
    }

    //Отрисовка части локации с размерами width и height вокруг камеры
    public void render(int width, int height) {
        render((int) camera.getX(), (int) camera.getY(), width, height);
    }

    //Отрисовка части локации с размерами width и height вокруг координат (x;y)
    public void render(int x, int y, int width, int height) {
        background.render(x, y, width, height, camera);
        super.render(x, y, width, height);
        guiLocationFrame.render();
    }

    public void update(long delta) {
        beforeUpdateActions.forEach(Runnable::run);
        super.update(delta);
        guiLocationFrame.update();
        afterUpdateActions.forEach(Runnable::run);

        beforeUpdateActions.clear();
        afterUpdateActions.clear();
    }

    //Уничтожение локации и всех объектов в локации
    public void destroy() {
        super.destroy();
        guiLocationFrame.destroy();
    }

    //TODO тоже вынести в отдельный класс, который занимается Updatе-ом (он же расчитывает порядок обновления компонентов и т.п.)
    private final Set<Runnable> beforeUpdateActions = new HashSet<>();
    private final Set<Runnable> afterUpdateActions = new HashSet<>();

    public void runBeforeUpdateOnce(Runnable runnable) {
        beforeUpdateActions.add(runnable);
    }

    public void runAfterUpdateOnce(Runnable runnable) {
        afterUpdateActions.add(runnable);
    }
}
