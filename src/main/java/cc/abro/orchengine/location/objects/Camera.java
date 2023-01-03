package cc.abro.orchengine.location.objects;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.util.Vector2;

import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Camera {

    //Абсолютная позиция камеры в локации, отрисовка происходит вокруг этой позиции
    private double x, y;
    //Объект, за которым следует камера (если не null, то вместо x и y будут возвращаться координаты объекта)
    private GameObject followObject = null;
    //Если true, то при приближении к границе локации будет держаться на расстояние от границы, чтобы была видна только локация
    private boolean visibleLocationOnly;
    //Если true, то звук будет считываться будто от следуемого объекта, а не от камеры.
    //Они могут находиться в разных местах при приближении к границе локации.
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
    public Vector2<Integer> toRelativePosition(Vector2<Integer> absolutePosition) {
        Vector2<Integer> relativePosition = new Vector2<>();
        relativePosition.x = (int) (Context.getService(Render.class).getWidth() / 2 - (getX() - absolutePosition.x));
        relativePosition.y = (int) (Context.getService(Render.class).getHeight() / 2 - (getY() - absolutePosition.y));

        return relativePosition;
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
        int width = Context.getService(Render.class).getWidth();
        int height = Context.getService(Render.class).getHeight();
        int widthMap = Context.getService(LocationManager.class).getActiveLocation().getWidth();
        int heightMap = Context.getService(LocationManager.class).getActiveLocation().getHeight();

        if (hasFollowObject()) {
            x = followObject.getComponent(Position.class).x;
            y = followObject.getComponent(Position.class).y;
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Optional<GameObject> getFollowObject() {
        return Optional.ofNullable(followObject);
    }

    public void setFollowObject(GameObject gameObject) {
        followObject = gameObject;
    }

    public void deleteFollowObject() {
        setFollowObject(null);
    }

    public boolean hasFollowObject() {
        return followObject != null;
    }

    public boolean isVisibleLocationOnly() {
        return visibleLocationOnly;
    }

    public void setVisibleLocationOnly(boolean visibleLocationOnly) {
        this.visibleLocationOnly = visibleLocationOnly;
    }

    public boolean isSoundOnFollowingObject() {
        return soundOnFollowingObject;
    }

    public void setSoundOnFollowingObject(boolean soundOnFollowingObject) {
        this.soundOnFollowingObject = soundOnFollowingObject;
    }
}
