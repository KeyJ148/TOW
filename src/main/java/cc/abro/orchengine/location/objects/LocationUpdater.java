package cc.abro.orchengine.location.objects;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Position;

public class LocationUpdater {

    //Объект, вокруг которого происходит обновление
    private final GameObject followObject;
    //Радиус в котором будут обновляться объекты. Если 0, то будет обновляться только followObject.
    private int radius;

    public LocationUpdater(GameObject followObject) {
        this(followObject, 0);
    }

    public LocationUpdater(GameObject followObject, int radius) {
        this.followObject = followObject;
        this.radius = radius;
    }

    public double getX() {
        return followObject.getComponent(Position.class).x;
    }

    public double getY() {
        return followObject.getComponent(Position.class).y;
    }

    public GameObject getFollowObject() {
        return followObject;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isOneObjectUpdater() {
        return radius == 0;
    }
}
