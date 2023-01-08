package cc.abro.tow.client.map.objects.box;

import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.objects.textured.TexturedMapObject;
import lombok.Getter;

public class BoxMapObject extends TexturedMapObject {

    @Getter
    private final int boxType;

    public BoxMapObject(int id, int x, int y, int z, String type, Texture texture, double direction, int boxType) {
        super(id, x, y, z, type, texture, direction);
        this.boxType = boxType;
    }
}
