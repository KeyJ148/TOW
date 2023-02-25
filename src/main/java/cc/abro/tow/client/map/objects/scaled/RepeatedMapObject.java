package cc.abro.tow.client.map.objects.scaled;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.render.RepeatableSpriteRender;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.MapObject;

public class RepeatedMapObject extends MapObject {

    public RepeatedMapObject(Location location, int id, int x, int y, int z, String type, Texture texture, double direction, boolean unsuitable, int width, int height) {
        super(location,id, x, y, type);
        setDirection(direction);
        RepeatableSpriteRender sprite = new RepeatableSpriteRender(texture, z, unsuitable);
        sprite.setWidth(width);
        sprite.setHeight(height);
        addComponent(sprite);
    }
}
