package cc.abro.tow.client.tanks.equipment.bullet1;

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

    private final Map<String, BulletCreator> bulletCreatorByName = new HashMap<>();

    public void init() {
        var bulletCreators = ReflectionUtils.createInstances(
                annotationScanService.getClassesWithAnnotations(StoredBulletCreator.class));

        bulletCreators.stream()
                .filter(bulletCreator -> bulletCreator instanceof BulletCreator)
                .map(bulletCreator -> (BulletCreator) bulletCreator)
                .forEach(this::addBulletCreator);
    }

    public void addBulletCreator(BulletCreator bulletCreator) {
        String name = bulletCreator.getName();
        if (bulletCreatorByName.containsKey(name)) {
            log.error("Bullet \"" + name + "\" already exists");
            throw new IllegalStateException("Bullet \"" + name + "\" already exists");
        }

        bulletCreatorByName.put(name, bulletCreator);
    }

    public BulletCreator getBulletCreator(String name) {
        if (!bulletCreatorByName.containsKey(name)) {
            log.error("Not found Bullet for name: \"" + name + "\"");
            throw new IllegalArgumentException("Not found Bullet for name: \"" + name + "\"");
        }

        return bulletCreatorByName.get(name);
    }
}
