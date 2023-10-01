package cc.abro.tow.client.tanks.equipment.armor;

import cc.abro.orchengine.context.GameService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@GameService
public class ArmorCreatorsStorage {

    private final Map<String, ArmorCreator<? extends ArmorSpecification>> armorCreatorByType = new HashMap<>();

    public void addArmorCreator(ArmorCreator<? extends ArmorSpecification> armorCreator) {
        String type = armorCreator.getType();
        if (armorCreatorByType.containsKey(type)) {
            log.error("ArmorCreator \"" + type + "\" already exists");
            throw new IllegalStateException("ArmorCreator \"" + type + "\" already exists");
        }

        armorCreatorByType.put(type, armorCreator);
    }

    public ArmorCreator<? extends ArmorSpecification> getArmorCreator(String type) {
        if (!armorCreatorByType.containsKey(type)) {
            log.error("Not found ArmorCreator for type: \"" + type + "\"");
            throw new IllegalArgumentException("Not found ArmorCreator for type: \"" + type + "\"");
        }

        return armorCreatorByType.get(type);
    }
}
