package cc.abro.tow.client.map;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import lombok.Getter;

public abstract class MapObject extends GameObject {

    @Getter
    private final int id;
    @Getter
    private final String type;

    public MapObject(Location location, int id, int x, int y, String type) {
        super(location, x, y);
        setDirection(0);
        this.id = id;
        this.type = type;
    }
}