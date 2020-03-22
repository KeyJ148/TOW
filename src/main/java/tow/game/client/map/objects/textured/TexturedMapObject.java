package tow.game.client.map.objects.textured;

import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.SpriteRender;
import tow.engine.resources.textures.Texture;
import tow.game.client.map.MapObject;

public class TexturedMapObject extends MapObject {

    public TexturedMapObject(int id, int x, int y, int z, String type, Texture texture, double direction) {
        super(id, x, y, z, type);
        getComponent(Position.class).setDirectionDraw(direction);
        setComponent(new SpriteRender(texture));
    }
}
