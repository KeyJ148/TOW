package cc.abro.tow.client.map.objects.road;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.scaled.RepeatedMapObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

public class RoadMapObjectCreator extends RepeatedMapObjectCreator {

    @Override
    public String getType() {
        return "road";
    }

    @Override
    public MapObject createMapObject(Location location, MapObjectSpecification mapObjectSpecification) {
        return new RoadMapObject(
                location,
                mapObjectSpecification.id(),
                mapObjectSpecification.x(),
                mapObjectSpecification.y(),
                mapObjectSpecification.z(),
                mapObjectSpecification.type(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification),
                getUnsuitable(mapObjectSpecification),
                getWidth(mapObjectSpecification),
                getHeight(mapObjectSpecification));
    }
}
