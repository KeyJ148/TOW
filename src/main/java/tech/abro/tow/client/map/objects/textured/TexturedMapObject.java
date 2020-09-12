package tech.abro.tow.client.map.objects.textured;

import tech.abro.orchengine.gameobject.components.Position;
import tech.abro.orchengine.gameobject.components.render.SpriteRender;
import tech.abro.orchengine.resources.textures.Texture;
import tech.abro.tow.client.map.MapObject;

public class TexturedMapObject extends MapObject {

    public TexturedMapObject(int id, int x, int y, int z, String type, Texture texture, double direction) {
        super(id, x, y, z, type);
        getComponent(Position.class).setDirectionDraw(direction);
        setComponent(new SpriteRender(texture));
    }
}
