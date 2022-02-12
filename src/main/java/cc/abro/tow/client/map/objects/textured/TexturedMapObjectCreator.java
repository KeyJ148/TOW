package cc.abro.tow.client.map.objects.textured;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.factory.MapObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

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

    protected double getDirection(MapObjectSpecification mapObjectSpecification) {
        return (double) mapObjectSpecification.getParameters().get("direction");
    }

    protected Texture getTexture(MapObjectSpecification mapObjectSpecification) {
        String textureName = (String) mapObjectSpecification.getParameters().get("texture");
        return Context.getService(SpriteStorage.class).getSprite(textureName).getTexture();
    }
}
