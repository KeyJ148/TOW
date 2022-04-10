package cc.abro.tow.client.map.objects.collised;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

public class CollisedMapObjectCreator extends TexturedMapObjectCreator {

    @Override
    public String getType() {
        return "collised";
    }

    @Override
    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        return new CollisedMapObject(
                mapObjectSpecification.getId(),
                mapObjectSpecification.getX(),
                mapObjectSpecification.getY(),
                mapObjectSpecification.getZ(),
                mapObjectSpecification.getType(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification),
                getMask(mapObjectSpecification));
    }

    protected Mask getMask(MapObjectSpecification mapObjectSpecification) {
        String textureName = (String) mapObjectSpecification.getParameters().get("texture");
        return Context.getService(SpriteStorage.class).getSprite(textureName).getMask();
    }
}
