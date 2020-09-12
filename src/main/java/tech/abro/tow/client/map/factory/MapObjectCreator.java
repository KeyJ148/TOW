package tech.abro.tow.client.map.factory;

import tech.abro.tow.client.map.MapObject;
import tech.abro.tow.client.map.specification.MapObjectSpecification;

public interface MapObjectCreator {

    String getType();
    MapObject createMapObject(MapObjectSpecification mapObjectSpecification);
}
