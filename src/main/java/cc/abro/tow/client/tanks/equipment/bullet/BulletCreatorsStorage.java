package cc.abro.tow.client.tanks.equipment.bullet;

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
public class BulletCreatorsStorage {

    private final AnnotationScanService annotationScanService;

    private final Map<String, BulletCreator<? extends BulletSpecification>> bulletCreatorByType = new HashMap<>();

    public void init() {
        var bulletCreators = ReflectionUtils.createInstances(
                annotationScanService.getClassesWithAnnotations(StoredBulletCreator.class));

        bulletCreators.stream()
                .filter(bulletCreator -> bulletCreator instanceof BulletCreator)
                .map(bulletCreator -> (BulletCreator<?>) bulletCreator)
                .forEach(this::addBulletCreator);
    }

    public void addBulletCreator(BulletCreator<? extends BulletSpecification> bulletCreator) {
        String type = bulletCreator.getType();
        if (bulletCreatorByType.containsKey(type)) {
            log.error("BulletCreator \"" + type + "\" already exists");
            throw new IllegalStateException("BulletCreator \"" + type + "\" already exists");
        }

        bulletCreatorByType.put(type, bulletCreator);
    }

    public BulletCreator<? extends BulletSpecification> getBulletCreator(String type) {
        if (!bulletCreatorByType.containsKey(type)) {
            log.error("Not found BulletCreator for type: \"" + type + "\"");
            throw new IllegalArgumentException("Not found BulletCreator for type: \"" + type + "\"");
        }

        return bulletCreatorByType.get(type);
    }
}
