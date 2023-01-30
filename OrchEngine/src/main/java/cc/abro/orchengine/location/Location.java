package cc.abro.orchengine.location;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.location.objects.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Location {

    @Getter
    private final int width, height;
    @Getter
    private final Camera camera; //Положение камеры в этой локации
    /**
     * Объекты, вокруг которых надо вызывать update.
     * Можно указать объект, и указать радиус ноль. Тогда всегда будет обновляться конкретный объект.
     * Это не эквивалентно unsuitableObjects из {@link Layer}, т.к. объекты в этой мапе могут только обновляться, но не
     * рендериться. При этом все объекты из сета unsuitableObjects из {@link Layer} обязаны находиться в этой мапе.
     */
    private final Map<GameObject, LocationUpdater> locationUpdaters = new HashMap<>();

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

    public void add(GameObject gameObject) {
        if (gameObject.getLocation() != null) {
            gameObject.getLocation().remove(gameObject);
        }
        gameObject.getLocationHolder().setLocation(this);
        objectsContainer.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        objectsContainer.remove(gameObject);
        locationUpdaters.remove(gameObject);
    }

    public void addUnsuitableObject(GameObject gameObject) {
        locationUpdaters.put(gameObject, new LocationUpdater(gameObject));
        objectsContainer.addUnsuitableObject(gameObject);
    }

    public void update(long delta) {
        objectsContainer.update(delta, locationUpdaters.values());
        guiLocationFrame.update();
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

    @Deprecated
    public Set<GameObject> getObjects() {
        return objectsContainer.getObjects();
    }

    //Уничтожение локации и всех объектов в локации
    public void destroy() {
        objectsContainer.destroy();
        guiLocationFrame.destroy();
    }

    public Statistic getStatistic() {
        return objectsContainer.getStatistic();
    }

    public void add(LocationUpdater locationUpdater) {
        locationUpdaters.put(locationUpdater.getFollowObject(), locationUpdater);
    }

    public void remove(LocationUpdater locationUpdater) {
        locationUpdaters.remove(locationUpdater.getFollowObject());
    }

    /**
     * Используется, чтобы только из данного класса можно было вызывать setLocation у игровых объектов
     */
    public static final class ObjectHolder {

        private Location location;

        public Location getLocation() {
            return location;
        }

        private void setLocation(Location location) {
            this.location = location;
        }
    }

    public record Statistic(Map<Integer, Integer> chunksUpdatedByLayerZ,
                            Map<Integer, Integer> objectsUpdatedByLayerZ,
                            Map<Integer, Integer> chunksRenderedByLayerZ,
                            Map<Integer, Integer> objectsRenderedByLayerZ,
                            Map<Integer, Integer> unsuitableObjectsRenderedByLayerZ) {
    }
}
