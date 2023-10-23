package cc.abro.tow.client.tanks.equipment.gun;

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
public class GunSpecificationStorage {

    private static final String CONFIGS_PATH = "configs/game/gun/";

    private final GunCreatorsStorage gunCreatorsStorage;
    private final Map<String, GunSpecification> gunSpecificationByName = new HashMap<>();

    public GunSpecificationStorage(GunCreatorsStorage gunCreatorsStorage) {
        this.gunCreatorsStorage = gunCreatorsStorage;
    }

    public void init() {
        try {
            List<String> gunSpecificationFilenames = ResourceLoader.scanResources(CONFIGS_PATH);
            log.info("Found " + (gunSpecificationFilenames.size() - 1) + " gun specifications");
            for (String gunSpecificationFilename : gunSpecificationFilenames) {
                if (gunSpecificationFilename.endsWith(".json")) {
                    log.debug("Loading gun specification: " + gunSpecificationFilename);
                    String gunSpecificationFilepath = CONFIGS_PATH + gunSpecificationFilename;
                    String gunSpecificationType = JsonContainerLoader.loadInternalFile(gunSpecificationFilepath)
                            .get("type").asText();
                    GunSpecification gunSpecification = loadGunSpecification(
                            gunSpecificationType, gunSpecificationFilepath);
                    String gunName = gunSpecificationFilename.substring(0,
                            gunSpecificationFilename.length() - ".json".length());
                    gunSpecificationByName.put(gunName, gunSpecification);
                }
            }
        } catch (IOException | URISyntaxException e) {
            log.error("Error when loading gun specifications", e);
        }
    }

    public GunSpecification getGunSpecification(String name) {
        if (!gunSpecificationByName.containsKey(name)) {
            log.error("Gun specification \"" + name + "\" not found");
            return null;
        }

        return gunSpecificationByName.get(name);
    }

    public Map<String, GunSpecification> getAllGunSpecificationByName() {
        return Collections.unmodifiableMap(gunSpecificationByName);
    }

    private GunSpecification loadGunSpecification(String type, String filepath) {
        try {
            Class<?> gunSpecificationClass = gunCreatorsStorage.getGunCreator(type).getGunSpecificationClass();
            Object gunSpecification = JsonContainerLoader.loadInternalFile(gunSpecificationClass, filepath);
            log.debug("Load gun specification \"" + filepath + "\" completed");
            return (GunSpecification) gunSpecification;
        } catch (Exception e) {
            log.error("Gun specification \"" + filepath + "\" not loading", e);
            throw new IllegalStateException("Gun specification \"" + filepath + "\" not loading");
        }
    }
}
