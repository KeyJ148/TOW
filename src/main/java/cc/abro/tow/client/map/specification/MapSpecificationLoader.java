package cc.abro.tow.client.map.specification;

import cc.abro.orchengine.resources.JsonContainerLoader;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
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
            log.debug("Load map \"" + path + "\" completed");
            return mapSpecification;
        } catch (Exception e) {
            log.error("Map \"" + path + "\" not loading");
            return null;
        }
    }

    private static record MapContainer (int width, int height, MapObjectContainer[] mapObjectContainers) {}
    private static record MapObjectContainer (int x, int y, int z, String type, Map<String, Object> parameters) {}
}
