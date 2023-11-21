package cc.abro.tow.client.tanks.equipment;

import cc.abro.orchengine.context.GameService;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreationService;
import cc.abro.tow.client.tanks.equipment.armor.ArmorSpecificationStorage;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.BulletModifierComponent;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.BulletModifierCreationService;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.BulletModifierSpecificationStorage;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunCreationService;
import cc.abro.tow.client.tanks.equipment.gun.GunSpecificationStorage;
import cc.abro.tow.client.tanks.tank.Tank;
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
    private static final String DEFAULT_BULLET_NAME = "default";

    private final ArmorCreationService armorCreationService;
    private final ArmorSpecificationStorage armorSpecificationStorage;
    private final GunCreationService gunCreationService;
    private final GunSpecificationStorage gunSpecificationStorage;
    private final BulletModifierCreationService bulletModifierCreationService;
    private final BulletModifierSpecificationStorage bulletModifierSpecificationStorage;

    private final Random random = new Random();

    public ArmorComponent createNewArmor(Tank tank) {
        String currentArmorName = tank.getArmorComponent().getName();
        int currentGunSize = tank.getGunComponent().getSize();
        int currentGunTechLevel = tank.getGunComponent().getTechLevel();

        List<String> armorNames = armorSpecificationStorage.getAllArmorSpecificationByName().entrySet().stream()
                .filter(entry -> !entry.getKey().equals(currentArmorName))
                .filter(entry -> Math.abs(entry.getValue().getSize() - currentGunSize) <= 1)
                .filter(entry -> Math.abs(entry.getValue().getTechLevel() - currentGunTechLevel) <= 1)
                .map(Map.Entry::getKey)
                .toList();
        String randomArmorName = getRandomItem(armorNames);

        return createArmor(randomArmorName);
    }

    public GunComponent createNewGun(Tank tank) {
        String currentGunName = tank.getGunComponent().getName();
        int currentArmorSize = tank.getArmorComponent().getSize();
        int currentArmorTechLevel = tank.getArmorComponent().getTechLevel();
        int currentBulletTechLevel = tank.getBulletModifierComponent().getTechLevel();

        List<String> gunNames = gunSpecificationStorage.getAllGunSpecificationByName().entrySet().stream()
                .filter(entry -> !entry.getKey().equals(currentGunName))
                .filter(entry -> Math.abs(entry.getValue().getSize() - currentArmorSize) <= 1)
                .filter(entry -> Math.abs(entry.getValue().getTechLevel() - currentArmorTechLevel) <= 1)
                .filter(entry -> Math.abs(entry.getValue().getTechLevel() - currentBulletTechLevel) <= 1)
                .map(Map.Entry::getKey)
                .toList();
        String randomGunName = getRandomItem(gunNames);

        return createGun(randomGunName);
    }

    public BulletModifierComponent createNewBullet(Tank tank) {
        String currentBulletName = tank.getBulletModifierComponent().getName();
        int currentGunTechLevel = tank.getGunComponent().getTechLevel();

        List<String> bulletNames = tank.getGunComponent().getBulletMapping().keySet().stream()
                .filter(bullet -> !bullet.equals(currentBulletName))
                .filter(bullet -> Math.abs(bulletModifierSpecificationStorage.getBulletModifierSpecification(bullet).getTechLevel() -
                        currentGunTechLevel) <= 1)
                .toList();

        String randomBulletName = getRandomItem(bulletNames);

        return createBullet(randomBulletName);
    }

    public ArmorComponent createDefaultArmor() {
        return createArmor(DEFAULT_ARMOR_NAME);
    }

    public GunComponent createDefaultGun() {
        return createGun(DEFAULT_GUN_NAME);
    }

    public BulletModifierComponent createDefaultBullet() {
        return createBullet(DEFAULT_BULLET_NAME);
    }

    public ArmorComponent createArmor(String armorName) {
        return armorCreationService.createArmor(armorName);
    }

    public GunComponent createGun(String gunName) {
        return gunCreationService.createGun(gunName);
    }

    public BulletModifierComponent createBullet(String bulletName) {
        return bulletModifierCreationService.createBulletModifier(bulletName);
    }

    private <T> T getRandomItem(Collection<T> itemsCollection)  {
        int itemPosition = random.nextInt(itemsCollection.size());
        return IterableUtils.get(itemsCollection, itemPosition);
    }
}
