package cc.abro.tow.client.map.objects.textured;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.MapObject;

public class TexturedMapObject extends MapObject {

    public TexturedMapObject(Location location, int id, int x, int y, int z, String type, Texture texture, double direction, boolean unsuitable) {
        super(location, id, x, y, type);
        setDirection(direction);
        addComponent(new SpriteRender(texture, z, unsuitable));
    }
}
