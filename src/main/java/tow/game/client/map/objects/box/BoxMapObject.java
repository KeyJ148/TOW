package tow.game.client.map.objects.box;

import tow.engine.resources.textures.Texture;
import tow.game.client.map.objects.textured.TexturedMapObject;

public class BoxMapObject extends TexturedMapObject {

    private int boxType;

    public BoxMapObject(int id, int x, int y, int z, String type, Texture texture, double direction, int boxType) {
        super(id, x, y, z, type, texture, direction);
        this.boxType = boxType;
    }

    public int getBoxType() {
        return boxType;
    }
}
