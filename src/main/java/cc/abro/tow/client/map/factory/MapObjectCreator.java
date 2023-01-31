package cc.abro.tow.client.map.factory;

import cc.abro.orchengine.location.Location;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

public interface MapObjectCreator {

    String getType();

    MapObject createMapObject(Location location, MapObjectSpecification mapObjectSpecification);
}
