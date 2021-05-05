package cc.abro.tow.client.map.objects.destroyed;

import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.objects.textured.TexturedMapObject;

public class DestroyedMapObject extends TexturedMapObject {

    private double stability;

    public DestroyedMapObject(int id, int x, int y, int z, String type, Texture texture, double direction, double stability) {
        super(id, x, y, z, type, texture, direction);
        this.stability = stability;
    }

    public double getStability() {
        return stability;
    }
}
