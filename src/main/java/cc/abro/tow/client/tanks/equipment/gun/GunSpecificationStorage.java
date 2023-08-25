package cc.abro.tow.client.tanks.equipment.gun;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.resources.JsonContainerLoader;
import cc.abro.orchengine.resources.ResourceLoader;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;
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
        try {
            List<String> gunSpecificationFilenames = ResourceLoader.scanResources(CONFIGS_PATH);
            log.info("Found " + (gunSpecificationFilenames.size() - 1) + " gun specifications"); //TODO почему -1? Что лишнее?
            for (String gunSpecificationFilename : gunSpecificationFilenames) {
                if (gunSpecificationFilename.endsWith(".json")) { //TODO проверка точно нужна?
                    log.debug("Loading gun specification: " + gunSpecificationFilename);
                    String armorSpecificationFilepath = CONFIGS_PATH + gunSpecificationFilename;
                    record GunSpecificationType(String type) {}
                    GunSpecificationType armorSpecificationType = JsonContainerLoader.loadInternalFile(
                            GunSpecificationType.class, gunSpecificationFilename);
                    GunSpecification gunSpecification = loadGunSpecification(
                            armorSpecificationType.type, armorSpecificationFilepath);
                    gunSpecificationByName.put(gunSpecificationFilename, gunSpecification);
                }
            }
        } catch (IOException | URISyntaxException e) {
            log.error("Error when loading armor specifications", e);
        }
    }

    public GunSpecification getGunSpecification(String name) {
        if (!gunSpecificationByName.containsKey(name)) {
            log.error("Gun specification \"" + name + "\" not found");
            return null;
        }

        return gunSpecificationByName.get(name);
    }

    private GunSpecification loadGunSpecification(String type, String filepath) {
        try {
            Class<?> gunSpecificationClass = gunCreatorsStorage.getGunCreator(type).getGunSpecificationClass();
            Object gunSpecification = JsonContainerLoader.loadInternalFile(gunSpecificationClass, filepath);
            log.debug("Load gun specification \"" + filepath + "\" completed");
            return (GunSpecification) gunSpecification;
        } catch (Exception e) {
            log.error("Gun specification \"" + filepath + "\" not loading");
            throw new IllegalStateException("Gun specification \"" + filepath + "\" not loading");
        }
    }
}
