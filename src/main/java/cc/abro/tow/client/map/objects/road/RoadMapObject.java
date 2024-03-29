package cc.abro.tow.client.map.objects.road;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.objects.scaled.RepeatedMapObject;

public class RoadMapObject extends RepeatedMapObject {

    public RoadMapObject(Location location, int id, int x, int y, int z, String type, Texture texture, double direction, boolean unsuitable, int width, int height) {
        super(location, id, x, y, z, type, texture, direction, unsuitable, width, height);
    }
}
