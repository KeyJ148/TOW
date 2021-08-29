package cc.abro.tow.client.map.objects.collised;

import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.objects.textured.TexturedMapObject;

public class CollisedMapObject extends TexturedMapObject {

    public CollisedMapObject(int id, int x, int y, int z, String type, Texture texture, double direction, Mask mask) {
        super(id, x, y, z, type, texture, direction);
        setComponent(new Collision(mask));
    }
}
