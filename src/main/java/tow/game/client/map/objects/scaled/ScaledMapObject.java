package tow.game.client.map.objects.scaled;

import tow.engine.resources.textures.Texture;
import tow.game.client.map.objects.textured.TexturedMapObject;

public class ScaledMapObject extends TexturedMapObject {

    public ScaledMapObject(int id, int x, int y, int z, String type, Texture texture, double direction, int width, int height) {
        super(id, x, y, z, type, texture, direction);
        //TODO: переопределить создание рендера и позиции, возможно наследовать от MapObject
    }
}
