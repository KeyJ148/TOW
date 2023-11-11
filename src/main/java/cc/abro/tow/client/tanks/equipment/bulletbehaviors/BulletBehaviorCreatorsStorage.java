package cc.abro.tow.client.tanks.equipment.bulletbehaviors;

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
public class BulletBehaviorCreatorsStorage {

    private final AnnotationScanService annotationScanService;

    private final Map<String, BulletBehaviorCreator> bulletBehaviorCreatorByName = new HashMap<>();

    public void init() {
        var bulletBehaviors = ReflectionUtils.createInstances(
                annotationScanService.getClassesWithAnnotations(StoredBulletBehaviorCreator.class));

        bulletBehaviors.stream()
                .filter(bulletBehaviorCreator -> bulletBehaviorCreator instanceof BulletBehaviorCreator)
                .map(bulletBehaviorCreator -> (BulletBehaviorCreator) bulletBehaviorCreator)
                .forEach(this::addBulletBehaviorCreator);
    }

    public void addBulletBehaviorCreator(BulletBehaviorCreator bulletBehaviorCreator) {
        String name = bulletBehaviorCreator.getName();
        if (bulletBehaviorCreatorByName.containsKey(name)) {
            log.error("BulletBehavior \"" + name + "\" already exists");
            throw new IllegalStateException("BulletBehavior \"" + name + "\" already exists");
        }

        bulletBehaviorCreatorByName.put(name, bulletBehaviorCreator);
    }

    public BulletBehavior createBulletBehavior(String name) {
        if (!bulletBehaviorCreatorByName.containsKey(name)) {
            log.error("Not found BulletBehavior for name: \"" + name + "\"");
            throw new IllegalArgumentException("Not found BulletBehavior for name: \"" + name + "\"");
        }

        return bulletBehaviorCreatorByName.get(name).createBulletBehavior();
    }
}
