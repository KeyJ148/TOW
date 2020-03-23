package tow.game.client.map.objects.destroyed;

import tow.engine.Global;
import tow.engine.resources.textures.Texture;
import tow.game.client.map.MapObject;
import tow.game.client.map.factory.MapObjectCreator;
import tow.game.client.map.specification.MapObjectSpecification;

public class DestroyedMapObjectCreator implements MapObjectCreator {

    @Override
    public String getType() {
        return "destroyed";
    }

    @Override
    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        String textureName = (String) mapObjectSpecification.getParameters().get("texture");
        Texture texture = Global.spriteStorage.getSprite(textureName).getTexture();
        double direction = (double) mapObjectSpecification.getParameters().get("direction");
        double stability = (double) mapObjectSpecification.getParameters().get("stability");

        return new DestroyedMapObject(
                mapObjectSpecification.getId(),
                mapObjectSpecification.getX(),
                mapObjectSpecification.getY(),
                mapObjectSpecification.getZ(),
                mapObjectSpecification.getType(),
                texture,
                direction,
                stability);
    }
}
