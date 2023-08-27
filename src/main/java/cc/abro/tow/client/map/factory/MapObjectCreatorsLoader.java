package cc.abro.tow.client.map.factory;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.services.AnnotationScanService;
import cc.abro.orchengine.util.ReflectionUtils;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.map.objects.StoredObjectCreator;
import cc.abro.tow.client.map.objects.box.BoxMapObjectCreator;
import cc.abro.tow.client.map.objects.destroyed.DestroyedMapObjectCreator;
import cc.abro.tow.client.map.objects.road.RoadMapObjectCreator;
import cc.abro.tow.client.map.objects.scaled.RepeatedMapObjectCreator;
import cc.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class MapObjectCreatorsLoader {

    private final ClientData clientData;
    private final AnnotationScanService annotationScanService;

    public void load() {
        var creators = ReflectionUtils.CreateInstances(
                annotationScanService.getClassesWithAnnotations(StoredObjectCreator.class)
        );

        for(var creator : creators){
            if(creator instanceof MapObjectCreator objectCreator)
                clientData.mapObjectFactory.registryNewCreator(objectCreator);
        }
    }
}
