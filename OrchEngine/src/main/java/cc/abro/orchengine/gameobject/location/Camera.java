package cc.abro.orchengine.gameobject.location;

import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.services.ServiceConsumer;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Camera implements ServiceConsumer {

    //Абсолютная позиция камеры в локации, отрисовка происходит вокруг этой позиции
    @Setter
    private double x, y;
    //Объект, за которым следует камера (если не null, то вместо x и y будут возвращаться координаты объекта)
    @Setter
    private Positionable followObject = null;
    //Если true, то при приближении к границе локации будет держаться на расстояние от границы, чтобы была видна только локация
    @Getter
    @Setter
    private boolean visibleLocationOnly;
    //Если true, то звук будет считываться будто от следуемого объекта, а не от камеры.
    //Они могут находиться в разных местах при приближении к границе локации.
    @Getter
    @Setter
    private boolean soundOnFollowingObject;

    public Camera() {
        this(0, 0);
    }

    public Camera(int x, int y) {
        this(x, y, false, false);
    }

    public Camera(int x, int y, boolean visibleLocationOnly, boolean soundOnFollowingObject) {
        this.x = x;
        this.y = y;
        this.visibleLocationOnly = visibleLocationOnly;
        this.soundOnFollowingObject = soundOnFollowingObject;
    }

    //Преобразует координаты относительно угла карты в координаты относительно угла экрана (области видимости камеры)
    public Vector2<Double> toRelativePosition(Vector2<Double> absolutePosition) {
        Vector2<Double> relativePosition = new Vector2<>();
        relativePosition.x = getRender().getWidth() / 2 - (getX() - absolutePosition.x);
        relativePosition.y = getRender().getHeight() / 2 - (getY() - absolutePosition.y);

        return relativePosition;
    }

    //Преобразует координаты относительно угла экрана (области видимости камеры) в координаты относительно угла карты
    public Vector2<Double> toAbsolutePosition(Vector2<Double> relativePosition) {
        Vector2<Double> absolutePosition = new Vector2<>();
        absolutePosition.x = getX() - getRender().getWidth() / 2 + relativePosition.x;
        absolutePosition.y = getY() - getRender().getHeight() / 2 + relativePosition.y;

        return absolutePosition;
    }

    public double getX() {
        calcPosition();
        return x;
    }

    public double getY() {
        calcPosition();
        return y;
    }

    //Расчёт текущей позиции
    private void calcPosition() {
        int width = getRender().getWidth();
        int height = getRender().getHeight();
        int widthMap = getLocationManager().getActiveLocation().getWidth();
        int heightMap = getLocationManager().getActiveLocation().getHeight();

        if (hasFollowObject()) {
            x = followObject.getX();
            y = followObject.getY();
        }

        if (isVisibleLocationOnly()) {
            x = max(x, width / 2.0);
            y = max(y, height / 2.0);
            x = min(x, widthMap - width / 2.0);
            y = min(y, heightMap - height / 2.0);

            if (width > widthMap) {
                x = widthMap / 2.0;
            }
            if (height > heightMap) {
                y = heightMap / 2.0;
            }
        }
    }

    public Optional<Positionable> getFollowObject() {
        return Optional.ofNullable(followObject);
    }

    public void deleteFollowObject() {
        setFollowObject(null);
    }

    public boolean hasFollowObject() {
        return followObject != null;
    }
}
