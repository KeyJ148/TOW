package cc.abro.tow.client.tanks.equipment;

import cc.abro.orchengine.context.GameService;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreationService;
import cc.abro.tow.client.tanks.equipment.armor.ArmorSpecificationStorage;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunCreationService;
import cc.abro.tow.client.tanks.equipment.gun.GunSpecificationStorage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

@GameService
@RequiredArgsConstructor
public class EquipmentService {

    private static final String DEFAULT_ARMOR_NAME = "default";
    private static final String DEFAULT_GUN_NAME = "default";

    private final ArmorCreationService armorCreationService;
    private final ArmorSpecificationStorage armorSpecificationStorage;
    private final GunCreationService gunCreationService;
    private final GunSpecificationStorage gunSpecificationStorage;

    private final Random random = new Random();

    public ArmorComponent createNewArmor(Tank tank) {
        String currentArmorName = tank.getArmorComponent().getName();
        int currentGunSize = tank.getGunComponent().getSize();

        List<String> armorNames = armorSpecificationStorage.getAllArmorSpecificationByName().entrySet().stream()
                .filter(entry -> !entry.getKey().equals(currentArmorName))
                .filter(entry -> Math.abs(entry.getValue().getSize() - currentGunSize) <= 1)
                .map(Map.Entry::getKey)
                .toList();
        String randomArmorName = getRandomItem(armorNames);

        return armorCreationService.createArmor(randomArmorName);
    }

    public GunComponent createNewGun(Tank tank) {
        String currentGunName = tank.getGunComponent().getName();
        int currentArmorSize = tank.getArmorComponent().getSize();

        List<String> gunNames = gunSpecificationStorage.getAllGunSpecificationByName().entrySet().stream()
                .filter(entry -> !entry.getKey().equals(currentGunName))
                .filter(entry -> Math.abs(entry.getValue().getSize() - currentArmorSize) <= 1)
                .map(Map.Entry::getKey)
                .toList();
        String randomGunName = getRandomItem(gunNames);

        return gunCreationService.createGun(randomGunName);
    }

    public void createNewBullet(Tank tank) {

    }

    public ArmorComponent createDefaultArmor() {
        return armorCreationService.createArmor(DEFAULT_ARMOR_NAME);
    }

    public GunComponent createDefaultGun() {
        return gunCreationService.createGun(DEFAULT_GUN_NAME);
    }

    public ArmorComponent createArmor(String armorName) {
        return armorCreationService.createArmor(armorName);
    }

    public GunComponent createGun(String gunName) {
        return gunCreationService.createGun(gunName);
    }

    private <T> T getRandomItem(Collection<T> itemsCollection)  {
        int itemPosition = random.nextInt(itemsCollection.size());
        return IterableUtils.get(itemsCollection, itemPosition);
    }
}
