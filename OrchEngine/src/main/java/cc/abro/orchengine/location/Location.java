package cc.abro.orchengine.location;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.location.objects.Background;
import cc.abro.orchengine.location.objects.Camera;
import cc.abro.orchengine.location.objects.Chunk;
import cc.abro.orchengine.location.objects.ObjectsContainer;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class Location {

    @Getter
    private final int width, height;
    @Getter
    private final Camera camera; //Положение камеры в этой локации

    private final ObjectsContainer objectsContainer; //Массив со всеми чанками и объектами
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
        this.width = width;
        this.height = height;

        camera = new Camera(width / 2, height / 2);
        objectsContainer = new ObjectsContainer(chunkSize);
        guiLocationFrame = new GuiLocationFrame();
        background = new Background();
    }

    public Location(int width, int height, Camera camera, ObjectsContainer objectsContainer,
                    GuiLocationFrame guiLocationFrame, Background background) {
        this.width = width;
        this.height = height;

        this.camera = camera;
        this.objectsContainer = objectsContainer;
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
        objectsContainer.render(x, y, width, height);
        guiLocationFrame.render();
    }

    //Проверка и при необходимости обновление объекта при перемещении из чанка в чанк
    public void checkGameObjectChunkChanged(GameObject gameObject) {
        if (gameObject.getLocation() == this) {
            objectsContainer.checkGameObjectChunkChanged(gameObject);
        }
    }

    public void update(long delta) {
        beforeUpdateActions.forEach(Runnable::run);
        objectsContainer.update(delta);
        guiLocationFrame.update();
        afterUpdateActions.forEach(Runnable::run);

        beforeUpdateActions.clear();
        afterUpdateActions.clear();
    }

    //Уничтожение локации и всех объектов в локации
    public void destroy() {
        objectsContainer.destroy();
        guiLocationFrame.destroy();
    }

    /*
    Просто прокси методы //TODO избавиться от них? Сделать наследование Location от ObjectsContainer?
     */
    @Deprecated
    public Set<GameObject> getObjects() {
        return objectsContainer.getObjects();
    }

    public void add(GameObject gameObject) { //TODO УДАЛИТЬ ВСЕ ИСПОЛЬЗОВАНИЯ КРОМЕ КЛАССА GAMEOBJECT. СДЕЛАТЬ ПРОВЕРКУ, ЧТО ОБЪЕКТ УЖЕ НЕ БЫЛ ДОБАВЛЕН В ЭТУ ЛОКАЦИЮ.
        if (gameObject.getLocation() != this) {
            throw new IllegalStateException("GameObject has wrong Location");
        }
        objectsContainer.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        if (gameObject.getLocation() != this) {
            throw new IllegalStateException("GameObject has wrong Location");
        }
        objectsContainer.remove(gameObject);
    }

    public void addUnsuitableObject(GameObject gameObject) {
        objectsContainer.addUnsuitableObject(gameObject);
    }

    public ObjectsContainer.Statistic getStatistic() {
        return objectsContainer.getStatistic();
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
