package tech.abro.tow.client.map.factory;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.Loader;
import tech.abro.orchengine.logger.Logger;
import tech.abro.tow.client.map.MapObject;
import tech.abro.tow.client.map.specification.MapObjectSpecification;

import java.util.HashMap;
import java.util.Map;

public class MapObjectFactory {

    private Map<String, MapObjectCreator> mapObjectCreatorByType = new HashMap<>();

    public void registryNewCreator(MapObjectCreator mapObjectCreator){
        if (mapObjectCreatorByType.containsKey(mapObjectCreator.getType())){
            Global.logger.println("MapObjectCreator \"" + mapObjectCreator.getType() + "\" already exists", Logger.Type.ERROR);
            Loader.exit();
        }

        mapObjectCreatorByType.put(mapObjectCreator.getType(), mapObjectCreator);
    }

    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification){
        if (!mapObjectCreatorByType.containsKey(mapObjectSpecification.getType())){
            Global.logger.print("MapObjectCreator \"" + mapObjectSpecification.getType() + "\" not found", Logger.Type.ERROR);
            return null;
        }

        return mapObjectCreatorByType.get(mapObjectSpecification.getType()).createMapObject(mapObjectSpecification);
    }

}
