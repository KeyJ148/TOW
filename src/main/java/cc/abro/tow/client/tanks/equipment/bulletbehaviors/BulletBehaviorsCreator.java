package cc.abro.tow.client.tanks.equipment.bulletbehaviors;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.services.AnnotationScanService;
import cc.abro.orchengine.util.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Log4j2
@GameService
@RequiredArgsConstructor
public class BulletBehaviorsCreator {

    private final AnnotationScanService annotationScanService;

    private final Map<String, Supplier<BulletBehavior>> bulletBehaviorByName = new HashMap<>();

    public void init() {
        var bulletBehaviors = ReflectionUtils.createInstances(
                annotationScanService.getClassesWithAnnotations(StoredBulletBehavior.class));

        bulletBehaviors.stream()
                .filter(bulletBehavior -> bulletBehavior instanceof BulletBehavior)
                .map(bulletBehavior -> (BulletBehavior) bulletBehavior)
                .forEach(this::addBulletBehavior);
    }

    public void addBulletBehavior(BulletBehavior bulletBehavior) {
        String name = bulletBehavior.getName();
        if (bulletBehaviorByName.containsKey(name)) {
            log.error("BulletBehavior \"" + name + "\" already exists");
            throw new IllegalStateException("BulletBehavior \"" + name + "\" already exists");
        }

        bulletBehaviorByName.put(name, bulletBehavior::createInstance);
    }

    public BulletBehavior createBulletBehavior(String name) {
        if (!bulletBehaviorByName.containsKey(name)) {
            log.error("Not found BulletBehavior for name: \"" + name + "\"");
            throw new IllegalArgumentException("Not found BulletBehavior for name: \"" + name + "\"");
        }

        return bulletBehaviorByName.get(name).get();
    }
}
