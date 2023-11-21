package cc.abro.tow.client.tanks.equipment.bulletmodifier;

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
public class BulletModifierCreatorsStorage {

    private final AnnotationScanService annotationScanService;

    private final Map<String, BulletModifierCreator<? extends BulletModifierSpecification>>
            bulletModifierCreatorByType = new HashMap<>();

    public void init() {
        var bulletModifierCreators = ReflectionUtils.createInstances(
                annotationScanService.getClassesWithAnnotations(StoredBulletModifierCreator.class));

        bulletModifierCreators.stream()
                .filter(bulletModifierCreator -> bulletModifierCreator instanceof BulletModifierCreator)
                .map(bulletModifierCreator -> (BulletModifierCreator<?>) bulletModifierCreator)
                .forEach(this::addBulletModifierCreator);
    }

    public void addBulletModifierCreator(
            BulletModifierCreator<? extends BulletModifierSpecification> bulletModifierCreator
    ) {
        String type = bulletModifierCreator.getType();
        if (bulletModifierCreatorByType.containsKey(type)) {
            log.error("BulletModifierCreator \"" + type + "\" already exists");
            throw new IllegalStateException("BulletModifierCreator \"" + type + "\" already exists");
        }

        bulletModifierCreatorByType.put(type, bulletModifierCreator);
    }

    public BulletModifierCreator<? extends BulletModifierSpecification> getBulletModifierCreator(String type) {
        if (!bulletModifierCreatorByType.containsKey(type)) {
            log.error("Not found BulletModifierCreator for type: \"" + type + "\"");
            throw new IllegalArgumentException("Not found BulletModifierCreator for type: \"" + type + "\"");
        }

        return bulletModifierCreatorByType.get(type);
    }
}
