package tow.game.client.map.factory;

import tow.engine.Global;
import tow.engine.Loader;
import tow.engine.logger.Logger;
import tow.game.client.map.MapObject;
import tow.game.client.map.specification.MapObjectSpecification;

import java.util.HashMap;
import java.util.Map;

public class MapObjectFactory {

    private Map<String, MapObjectCreator> mapObjectCreatorByType = new HashMap<>();

    public void registryNewType(String type, MapObjectCreator mapObjectCreator){
        if (mapObjectCreatorByType.containsKey(type)){
            Global.logger.println("MapObjectCreator \"" + type + "\" already exists", Logger.Type.ERROR);
            Loader.exit();
        }

        mapObjectCreatorByType.put(type, mapObjectCreator);
    }

    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification){
        if (!mapObjectCreatorByType.containsKey(mapObjectSpecification.getType())){
            Global.logger.print("MapObjectCreator \"" + mapObjectSpecification.getType() + "\" not found", Logger.Type.ERROR);
            return null;
        }

        return mapObjectCreatorByType.get(mapObjectSpecification.getType()).createMapObject(mapObjectSpecification);
    }

}
