package cc.abro.tow.client.map.objects.textured;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.location.Location;
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
    public MapObject createMapObject(Location location, MapObjectSpecification mapObjectSpecification) {
        return new TexturedMapObject(
                location,
                mapObjectSpecification.id(),
                mapObjectSpecification.x(),
                mapObjectSpecification.y(),
                mapObjectSpecification.z(),
                mapObjectSpecification.type(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification));
    }

    protected double getDirection(MapObjectSpecification mapObjectSpecification) {
        return ((Number) mapObjectSpecification.parameters().get("direction")).doubleValue();
    }

    protected Texture getTexture(MapObjectSpecification mapObjectSpecification) {
        String textureName = (String) mapObjectSpecification.parameters().get("texture");
        return Context.getService(SpriteStorage.class).getSprite(textureName).texture();
    }
}
