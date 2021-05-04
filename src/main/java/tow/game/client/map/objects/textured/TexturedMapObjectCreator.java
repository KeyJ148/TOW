package tow.game.client.map.objects.textured;

import tow.engine.Global;
import tow.engine.resources.textures.Texture;
import tow.game.client.map.MapObject;
import tow.game.client.map.factory.MapObjectCreator;
import tow.game.client.map.specification.MapObjectSpecification;

public class TexturedMapObjectCreator implements MapObjectCreator {

    @Override
    public String getType() {
        return "textured";
    }

    @Override
    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        return new TexturedMapObject(
                mapObjectSpecification.getId(),
                mapObjectSpecification.getX(),
                mapObjectSpecification.getY(),
                mapObjectSpecification.getZ(),
                mapObjectSpecification.getType(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification));
    }

    protected double getDirection(MapObjectSpecification mapObjectSpecification){
        return (double) mapObjectSpecification.getParameters().get("direction");
    }

    protected Texture getTexture(MapObjectSpecification mapObjectSpecification){
        String textureName = (String) mapObjectSpecification.getParameters().get("texture");
        return Global.spriteStorage.getSprite(textureName).getTexture();
    }


}
