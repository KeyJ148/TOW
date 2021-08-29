package cc.abro.tow.client.map.objects.textured;

import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.MapObject;

public class TexturedMapObject extends MapObject {

    public TexturedMapObject(int id, int x, int y, int z, String type, Texture texture, double direction) {
        super(id, x, y, z, type);
        getComponent(Position.class).setDirectionDraw(direction);
        setComponent(new SpriteRender(texture));
    }
}
