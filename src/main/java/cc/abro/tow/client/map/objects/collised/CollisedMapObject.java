package cc.abro.tow.client.map.objects.collised;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.map.objects.textured.TexturedMapObject;

public class CollisedMapObject extends TexturedMapObject {

    public CollisedMapObject(Location location, int id, int x, int y, int z, String type, Texture texture,
                             double direction, boolean unsuitable, Mask mask) {
        super(location, id, x, y, z, type, texture, direction, unsuitable);
        addComponent(new Collision(mask, CollidableObjectType.WALL));
    }
}
