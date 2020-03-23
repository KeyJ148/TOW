package tow.game.client.map.objects.box;

import tow.engine.Global;
import tow.engine.resources.textures.Texture;
import tow.game.client.map.MapObject;
import tow.game.client.map.factory.MapObjectCreator;
import tow.game.client.map.specification.MapObjectSpecification;

public class BoxMapObjectCreator implements MapObjectCreator {

    @Override
    public String getType() {
        return "box";
    }

    @Override
    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        String textureName = (String) mapObjectSpecification.getParameters().get("texture");
        Texture texture = Global.spriteStorage.getSprite(textureName).getTexture();
        double direction = (double) mapObjectSpecification.getParameters().get("direction");
        int boxType = (int) mapObjectSpecification.getParameters().get("boxType");

        return new BoxMapObject(
                mapObjectSpecification.getId(),
                mapObjectSpecification.getX(),
                mapObjectSpecification.getY(),
                mapObjectSpecification.getZ(),
                mapObjectSpecification.getType(),
                texture,
                direction,
                boxType);
    }
}
