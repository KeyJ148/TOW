package tow.game.client.map.objects.destroyed;

import tow.engine.resources.textures.Texture;
import tow.game.client.map.objects.textured.TexturedMapObject;

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
