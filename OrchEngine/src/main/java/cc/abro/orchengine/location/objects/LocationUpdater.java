package cc.abro.orchengine.location.objects;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Position;
import lombok.Getter;
import lombok.Setter;

public class LocationUpdater {

    //Объект, вокруг которого происходит обновление
    @Getter
    private final GameObject followObject;
    //Радиус в котором будут обновляться объекты. Если 0, то будет обновляться только followObject.
    @Getter
    @Setter
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

    public boolean isOneObjectUpdater() {
        return radius == 0;
    }
}
