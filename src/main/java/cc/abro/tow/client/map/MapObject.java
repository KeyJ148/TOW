package cc.abro.tow.client.map;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Position;
import lombok.Getter;

import java.util.List;

public abstract class MapObject extends GameObject {

    @Getter
    private final int id;
    @Getter
    private final String type;

    public MapObject(Location location, int id, int x, int y, int z, String type) {
        super(location, List.of(new Position(x, y, z, 0)));
        this.id = id;
        this.type = type;
    }
}