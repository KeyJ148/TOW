package cc.abro.tow.client.tanks.equipment.bulletmodifier;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.resources.JsonContainerLoader;
import cc.abro.orchengine.resources.ResourceLoader;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@GameService
public class BulletModifierSpecificationStorage {

    private static final String CONFIGS_PATH = "configs/game/bullet-modifier/";

    private final BulletModifierCreatorsStorage bulletModifierCreatorsStorage;
    private final Map<String, BulletModifierSpecification> bulletModifierSpecificationByName = new HashMap<>();

    public BulletModifierSpecificationStorage(BulletModifierCreatorsStorage bulletModifierCreatorsStorage) {
        this.bulletModifierCreatorsStorage = bulletModifierCreatorsStorage;
    }

    public void init() {
        try {
            List<String> bulletModifierSpecificationFilenames = ResourceLoader.scanResources(CONFIGS_PATH);
            log.info("Found " + (bulletModifierSpecificationFilenames.size() - 1) + " bullet modifier specifications");
            for (String bulletModifierSpecificationFilename : bulletModifierSpecificationFilenames) {
                if (bulletModifierSpecificationFilename.endsWith(".json")) {
                    log.debug("Loading bullet modifier specification: " + bulletModifierSpecificationFilename);
                    String bulletModifierSpecificationFilepath = CONFIGS_PATH + bulletModifierSpecificationFilename;
                    String bulletModifierSpecificationType = JsonContainerLoader
                            .loadInternalFile(bulletModifierSpecificationFilepath).get("type").asText();
                    BulletModifierSpecification bulletModifierSpecification = loadBulletModifierSpecification(
                            bulletModifierSpecificationType, bulletModifierSpecificationFilepath);
                    String bulletModifierName = bulletModifierSpecificationFilename.substring(0,
                            bulletModifierSpecificationFilename.length() - ".json".length());
                    bulletModifierSpecificationByName.put(bulletModifierName, bulletModifierSpecification);
                }
            }
        } catch (IOException | URISyntaxException e) {
            log.error("Error when loading bullet modifier specifications", e);
        }
    }

    public BulletModifierSpecification getBulletModifierSpecification(String name) {
        if (!bulletModifierSpecificationByName.containsKey(name)) {
            log.error("Bullet modifier specification \"" + name + "\" not found");
            return null;
        }

        return bulletModifierSpecificationByName.get(name);
    }

    public Map<String, BulletModifierSpecification> getAllBulletModifierSpecificationByName() {
        return Collections.unmodifiableMap(bulletModifierSpecificationByName);
    }

    private BulletModifierSpecification loadBulletModifierSpecification(String type, String filepath) {
        try {
            Class<?> bulletModifierSpecificationClass = bulletModifierCreatorsStorage.getBulletModifierCreator(type)
                    .getBulletModifierSpecificationClass();
            Object bulletModifierSpecification = JsonContainerLoader
                    .loadInternalFile(bulletModifierSpecificationClass, filepath);
            log.debug("Load bullet modifier specification \"" + filepath + "\" completed");
            return (BulletModifierSpecification) bulletModifierSpecification;
        } catch (Exception e) {
            log.error("Bullet modifier specification \"" + filepath + "\" not loading", e);
            throw new IllegalStateException("Bullet modifier specification \"" + filepath + "\" not loading");
        }
    }
}
