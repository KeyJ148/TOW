package cc.abro.tow.client.map.factory;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Loader;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.specification.MapObjectSpecification;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class MapObjectFactory {

    private Map<String, MapObjectCreator> mapObjectCreatorByType = new HashMap<>();

    public void registryNewCreator(MapObjectCreator mapObjectCreator) {
        if (mapObjectCreatorByType.containsKey(mapObjectCreator.getType())) {
            log.fatal("MapObjectCreator \"" + mapObjectCreator.getType() + "\" already exists");
            Loader.exit();
        }

        mapObjectCreatorByType.put(mapObjectCreator.getType(), mapObjectCreator);
    }

    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        if (!mapObjectCreatorByType.containsKey(mapObjectSpecification.getType())) {
            log.error("MapObjectCreator \"" + mapObjectSpecification.getType() + "\" not found");
            return null;
        }

        return mapObjectCreatorByType.get(mapObjectSpecification.getType()).createMapObject(mapObjectSpecification);
    }

}
