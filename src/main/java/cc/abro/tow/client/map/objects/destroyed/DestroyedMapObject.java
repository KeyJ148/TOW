package cc.abro.tow.client.map.objects.destroyed;

import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.objects.collised.CollisedMapObject;

public class DestroyedMapObject extends CollisedMapObject {

    private double stability;

    public DestroyedMapObject(int id, int x, int y, int z, String type, Texture texture, double direction, Mask mask, double stability) {
        super(id, x, y, z, type, texture, direction, mask);
        this.stability = stability;
    }

    public double getStability() {
        return stability;
    }
}
