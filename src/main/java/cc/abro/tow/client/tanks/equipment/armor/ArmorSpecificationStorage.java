package cc.abro.tow.client.tanks.equipment.armor;

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
public class ArmorSpecificationStorage {

    private static final String CONFIGS_PATH = "configs/game/armor/";

    private final ArmorCreatorsStorage armorCreatorsStorage;
    private final Map<String, ArmorSpecification> armorSpecificationByName = new HashMap<>();

    public ArmorSpecificationStorage(ArmorCreatorsStorage armorCreatorsStorage) {
        this.armorCreatorsStorage = armorCreatorsStorage;
        try {
            List<String> armorSpecificationFilenames = ResourceLoader.scanResources(CONFIGS_PATH);
            log.info("Found " + (armorSpecificationFilenames.size() - 1) + " armor specifications"); //TODO почему -1? Что лишнее?
            for (String armorSpecificationFilename : armorSpecificationFilenames) {
                if (armorSpecificationFilename.endsWith(".json")) { //TODO проверка точно нужна?
                    log.debug("Loading armor specification: " + armorSpecificationFilename);
                    String armorSpecificationFilepath = CONFIGS_PATH + armorSpecificationFilename;
                    record ArmorSpecificationType(String type) {}
                    ArmorSpecificationType armorSpecificationType = JsonContainerLoader.loadInternalFile(
                            ArmorSpecificationType.class, armorSpecificationFilename);
                    ArmorSpecification armorSpecification = loadArmorSpecification(
                            armorSpecificationType.type, armorSpecificationFilepath);
                    armorSpecificationByName.put(armorSpecificationFilename, armorSpecification);
                }
            }
        } catch (IOException | URISyntaxException e) {
            log.error("Error when loading armor specifications", e);
        }
    }

    public ArmorSpecification getArmorSpecification(String name) {
        if (!armorSpecificationByName.containsKey(name)) {
            log.error("Armor specification \"" + name + "\" not found");
            return null;
        }

        return armorSpecificationByName.get(name);
    }

    private ArmorSpecification loadArmorSpecification(String type, String filepath) {
        try {
            Class<?> armorSpecificationClass = armorCreatorsStorage.getArmorCreator(type).getArmorSpecificationClass();
            Object armorSpecification = JsonContainerLoader.loadInternalFile(armorSpecificationClass, filepath);
            log.debug("Load armor specification \"" + filepath + "\" completed");
            return (ArmorSpecification) armorSpecification;
        } catch (Exception e) {
            log.error("Armor specification \"" + filepath + "\" not loading");
            throw new IllegalStateException("Armor specification \"" + filepath + "\" not loading");
        }
    }
}
