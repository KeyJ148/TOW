package cc.abro.tow.client.map.objects.destroyed;

import cc.abro.orchengine.location.Location;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.collised.CollisedMapObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

public class DestroyedMapObjectCreator extends CollisedMapObjectCreator {

    @Override
    public String getType() {
        return "destroyed";
    }

    @Override
    public MapObject createMapObject(Location location, MapObjectSpecification mapObjectSpecification) {
        return new DestroyedMapObject(
                location,
                mapObjectSpecification.id(),
                mapObjectSpecification.x(),
                mapObjectSpecification.y(),
                mapObjectSpecification.z(),
                mapObjectSpecification.type(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification),
                getMask(mapObjectSpecification),
                getStability(mapObjectSpecification));
    }

    protected double getStability(MapObjectSpecification mapObjectSpecification) {
        return ((Number) mapObjectSpecification.parameters().get("stability")).doubleValue();
    }
}
