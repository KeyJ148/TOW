package cc.abro.tow.client.tanks.equipment.bullet;

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
public class BulletSpecificationStorage {

    private static final String CONFIGS_PATH = "configs/game/bullet/";

    private final BulletCreatorsStorage bulletCreatorsStorage;
    private final Map<String, BulletSpecification> bulletSpecificationByName = new HashMap<>();

    public BulletSpecificationStorage(BulletCreatorsStorage bulletCreatorsStorage) {
        this.bulletCreatorsStorage = bulletCreatorsStorage;
    }

    public void init() {
        try {
            List<String> bulletSpecificationFilenames = ResourceLoader.scanResources(CONFIGS_PATH);
            log.info("Found " + (bulletSpecificationFilenames.size() - 1) + " bullet specifications");
            for (String bulletSpecificationFilename : bulletSpecificationFilenames) {
                if (bulletSpecificationFilename.endsWith(".json")) {
                    log.debug("Loading bullet specification: " + bulletSpecificationFilename);
                    String bulletSpecificationFilepath = CONFIGS_PATH + bulletSpecificationFilename;
                    String bulletSpecificationType = JsonContainerLoader.loadInternalFile(bulletSpecificationFilepath)
                            .get("type").asText();
                    BulletSpecification bulletSpecification = loadBulletSpecification(
                            bulletSpecificationType, bulletSpecificationFilepath);
                    String bulletName = bulletSpecificationFilename.substring(0,
                            bulletSpecificationFilename.length() - ".json".length());
                    bulletSpecificationByName.put(bulletName, bulletSpecification);
                }
            }
        } catch (IOException | URISyntaxException e) {
            log.error("Error when loading bullet specifications", e);
        }
    }

    public BulletSpecification getBulletSpecification(String name) {
        if (!bulletSpecificationByName.containsKey(name)) {
            log.error("Bullet specification \"" + name + "\" not found");
            return null;
        }

        return bulletSpecificationByName.get(name);
    }

    public Map<String, BulletSpecification> getAllBulletSpecificationByName() {
        return Collections.unmodifiableMap(bulletSpecificationByName);
    }

    private BulletSpecification loadBulletSpecification(String type, String filepath) {
        try {
            Class<?> bulletSpecificationClass = bulletCreatorsStorage.getBulletCreator(type).getBulletSpecificationClass();
            Object bulletSpecification = JsonContainerLoader.loadInternalFile(bulletSpecificationClass, filepath);
            log.debug("Load bullet specification \"" + filepath + "\" completed");
            return (BulletSpecification) bulletSpecification;
        } catch (Exception e) {
            log.error("Bullet specification \"" + filepath + "\" not loading", e);
            throw new IllegalStateException("Bullet specification \"" + filepath + "\" not loading");
        }
    }
}
