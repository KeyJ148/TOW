package tow.game.client.map.factory;

import tow.game.client.map.MapObject;
import tow.game.client.map.specification.MapObjectSpecification;

public interface MapObjectCreator {

    String getType();

    MapObject createMapObject(MapObjectSpecification mapObjectSpecification);
}
