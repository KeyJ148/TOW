package cc.abro.tow.client.tanks.equipment.gun;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.services.AnnotationScanService;
import cc.abro.orchengine.util.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@GameService
@RequiredArgsConstructor
public class GunCreatorsStorage {

    private final AnnotationScanService annotationScanService;

    private final Map<String, GunCreator<? extends GunSpecification>> gunCreatorByType = new HashMap<>();

    public void init() {
        var gunCreators = ReflectionUtils.createInstances(
                annotationScanService.getClassesWithAnnotations(StoredGunCreator.class));

        gunCreators.stream()
                .filter(gunCreator -> gunCreator instanceof GunCreator)
                .map(gunCreator -> (GunCreator<?>) gunCreator)
                .forEach(this::addGunCreator);
    }

    public void addGunCreator(GunCreator<? extends GunSpecification> gunCreator) {
        String type = gunCreator.getType();
        if (gunCreatorByType.containsKey(type)) {
            log.error("GunCreator \"" + type + "\" already exists");
            throw new IllegalStateException("GunCreator \"" + type + "\" already exists");
        }

        gunCreatorByType.put(type, gunCreator);
    }

    public GunCreator<? extends GunSpecification> getGunCreator(String type) {
        if (!gunCreatorByType.containsKey(type)) {
            log.error("Not found GunCreator for type: \"" + type + "\"");
            throw new IllegalArgumentException("Not found GunCreator for type: \"" + type + "\"");
        }

        return gunCreatorByType.get(type);
    }
}
