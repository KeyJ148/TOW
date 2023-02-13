package cc.abro.tow.client.map.objects.collised;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Location;
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
    public MapObject createMapObject(Location location, MapObjectSpecification mapObjectSpecification) {
        return new CollisedMapObject(
                location,
                mapObjectSpecification.id(),
                mapObjectSpecification.x(),
                mapObjectSpecification.y(),
                mapObjectSpecification.z(),
                mapObjectSpecification.type(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification),
                getUnsuitable(mapObjectSpecification),
                getMask(mapObjectSpecification));
    }

    protected Mask getMask(MapObjectSpecification mapObjectSpecification) {
        String textureName = (String) mapObjectSpecification.parameters().get("texture");
        return Context.getService(SpriteStorage.class).getSprite(textureName).mask();
    }
}
