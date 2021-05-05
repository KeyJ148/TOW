package cc.abro.tow.client.map.specification;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.resources.JsonContainerLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class MapSpecificationLoader {

    private static final Logger log = LogManager.getLogger(AnimationRender.class);

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
