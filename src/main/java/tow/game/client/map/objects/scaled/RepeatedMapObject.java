package tow.game.client.map.objects.scaled;

import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.RepeatableSpriteRender;
import tow.engine.resources.textures.Texture;
import tow.game.client.map.MapObject;

public class RepeatedMapObject extends MapObject {

    public RepeatedMapObject(int id, int x, int y, int z, String type, Texture texture, double direction, int width, int height) {
        super(id, x, y, z, type);
        getComponent(Position.class).setDirectionDraw(direction);
        RepeatableSpriteRender sprite = new RepeatableSpriteRender(texture);
        sprite.setWidth(width);
        sprite.setHeight(height);
        setComponent(sprite);
        //TODO: переопределить создание рендера и позиции, возможно наследовать от MapObject
    }
}
