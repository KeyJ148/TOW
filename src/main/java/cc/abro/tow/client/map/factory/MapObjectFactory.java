package cc.abro.tow.client.map.factory;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.services.AnnotationScanService;
import cc.abro.orchengine.util.ReflectionUtils;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.StoredObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@GameService
@RequiredArgsConstructor
public class MapObjectFactory {

    private final AnnotationScanService annotationScanService;

    private final Map<String, MapObjectCreator> mapObjectCreatorByType = new HashMap<>();

    public void init() {
        var creators = ReflectionUtils.createInstances(
                annotationScanService.getClassesWithAnnotations(StoredObjectCreator.class));

        creators.stream()
                .filter(creator -> creator instanceof MapObjectCreator)
                .map(creator -> (MapObjectCreator) creator)
                .forEach(this::registryNewCreator);
    }

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
