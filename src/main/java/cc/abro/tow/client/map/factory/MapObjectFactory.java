package cc.abro.tow.client.map.factory;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.specification.MapObjectSpecification;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class MapObjectFactory {

    private final Map<String, MapObjectCreator> mapObjectCreatorByType = new HashMap<>();

    public void registryNewCreator(MapObjectCreator mapObjectCreator) {
        if (mapObjectCreatorByType.containsKey(mapObjectCreator.getType())) {
            log.fatal("MapObjectCreator \"" + mapObjectCreator.getType() + "\" already exists");
            throw new IllegalStateException("MapObjectCreator \"" + mapObjectCreator.getType() + "\" already exists");
        }

        mapObjectCreatorByType.put(mapObjectCreator.getType(), mapObjectCreator);
    }

    public MapObject createMapObject(Location location, MapObjectSpecification mapObjectSpecification) {
        if (!mapObjectCreatorByType.containsKey(mapObjectSpecification.type())) {
            log.error("MapObjectCreator \"" + mapObjectSpecification.type() + "\" not found");
            return null;
        }

        return mapObjectCreatorByType.get(mapObjectSpecification.type()).createMapObject(location, mapObjectSpecification);
    }

}
