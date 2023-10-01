package cc.abro.tow.client.tanks.equipment.gun;

import cc.abro.orchengine.context.GameService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@GameService
public class GunCreatorsStorage {

    private final Map<String, GunCreator<? extends GunSpecification>> gunCreatorByType = new HashMap<>();

    public void addGunCreator(GunCreator<? extends GunSpecification> gunCreator) {
        String type = gunCreator.getType();
        if (gunCreatorByType.containsKey(type)) {
            log.error("GunCreator \"" + type + "\" already exists");
            throw new IllegalStateException("GunCreator \"" + type + "\" already exists");
        }

        gunCreatorByType.put(type, gunCreator);
    }

    public GunCreator<? extends GunSpecification> getGunCreator(String type) {
        if (!gunCreatorByType.containsKey(type)) {
            log.error("Not found GunCreator for type: \"" + type + "\"");
            throw new IllegalArgumentException("Not found GunCreator for type: \"" + type + "\"");
        }

        return gunCreatorByType.get(type);
    }
}
