package cc.abro.tow.client.map.specification;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.logger.Logger;
import cc.abro.orchengine.resources.JsonContainerLoader;

import java.util.HashMap;
import java.util.Map;

public class MapSpecificationLoader {

    public static MapSpecification getMapSpecification(String path) {
        try {
            MapContainer mapContainer = JsonContainerLoader.loadExternalFile(MapContainer.class, path);

            Map<Integer, MapObjectSpecification> mapObjectSpecificationById = new HashMap<>();
            for (int i = 0; i < mapContainer.mapObjectContainers.length; i++) {
                MapObjectContainer mapObjectContainer = mapContainer.mapObjectContainers[i];
                mapObjectSpecificationById.put(i, new MapObjectSpecification(i, mapObjectContainer.x, mapObjectContainer.y,
                        mapObjectContainer.z, mapObjectContainer.type, mapObjectContainer.parameters));
            }

            MapSpecification mapSpecification = new MapSpecification(mapContainer.width, mapContainer.height, mapObjectSpecificationById);
            Global.logger.println("Load map \"" + path + "\" completed", Logger.Type.DEBUG);
            return mapSpecification;
        } catch (Exception e) {
            Global.logger.println("Map \"" + path + "\" not loading", Logger.Type.ERROR);
            return null;
        }
    }

    private static class MapContainer {
        public int width, height;
        public MapObjectContainer[] mapObjectContainers;
    }

    private static class MapObjectContainer {
        public int x, y, z;
        public String type;
        public Map<String, Object> parameters;
    }
}
