package cc.abro.tow.client.map.objects.scaled;

import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.RepeatableSpriteRender;
import cc.abro.orchengine.location.Location;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.MapObject;

public class RepeatedMapObject extends MapObject {

    public RepeatedMapObject(Location location, int id, int x, int y, int z, String type, Texture texture, double direction, int width, int height) {
        super(location,id, x, y, z, type);
        getComponent(Position.class).setDirectionDraw(direction);
        RepeatableSpriteRender sprite = new RepeatableSpriteRender(texture);
        sprite.setWidth(width);
        sprite.setHeight(height);
        addComponent(sprite);
    }
}
