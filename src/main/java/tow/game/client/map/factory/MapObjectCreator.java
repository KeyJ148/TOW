package tow.game.client.map.factory;

import tow.game.client.map.MapObject;
import tow.game.client.map.specification.MapObjectSpecification;

public interface MapObjectCreator {

    MapObject createMapObject(MapObjectSpecification mapObjectSpecification);
}
