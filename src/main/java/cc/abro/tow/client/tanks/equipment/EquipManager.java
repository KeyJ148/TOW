package cc.abro.tow.client.tanks.equipment;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Loader;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.tanks.player.Armor;
import cc.abro.tow.client.tanks.player.Bullet;
import cc.abro.tow.client.tanks.player.Gun;
import cc.abro.tow.client.tanks.player.Player;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/*
При добавление необходимо менять...
Броня:
Storage, tow.player.equipment.armor/, res/settings/armor
Оружие:
Storage, tow.player.equipment.gun/, res/settings/gun, res/settings/armor, res/settings/bullet
Патроны:
Storage, tow.player.equipment.bullet/, res/settings/bullet
*/

@Log4j2
public class EquipManager {

    private static Random random = new Random();

    private static final String[] ARMOR_PROPS = {
            "ADefault.properties",
            "ALight.properties",
            "AFortified.properties",
            "AVampire.properties",
            "AFury.properties",
            "AElephant.properties",
            "ARenegat.properties",
            "AMite.properties"
    };

    private static final String[] BULLET_PROPS = {
            "BDefault.properties",
            "BStreamlined.properties",
            "BFury.properties",
            "BMass.properties",
            "BSteel.properties",
            "BVampire.properties",
            "BMassSmall.properties",
            "BSquaremass.properties"
    };

    public static void newArmor(Player player) {
        //Получение валидного имени экипировки (Которое не равно текущему и соответствует пушке)
        String newArmorName = "";
        boolean exit;
        do {
            exit = false;

            String config = ARMOR_PROPS[random.nextInt(ARMOR_PROPS.length)];
            if (!config.contains(".properties")) continue;

            ConfigReader cr = new ConfigReader(Armor.PATH_SETTING + config);
            if (!contain(cr.findString("ALLOW_GUN").split(" "), ((Gun) player.gun).name)) continue;

            newArmorName = config.substring(0, config.lastIndexOf("."));
            if (!newArmorName.equals(((Armor) player.armor).name)) exit = true;
        } while (!exit);

        //Создание класса через рефлексию
        String newArmorClass = new ConfigReader(Armor.PATH_SETTING + newArmorName + ".properties").findString("CLASS");
        Armor newArmor = null;
        try {
            String newArmorFullPath = getClassPackage(player.armor) + "." + newArmorClass;
            newArmor = (Armor) Class.forName(newArmorFullPath).newInstance();
            newArmor.init(player, player.armor.getComponent(Position.class).x, player.armor.getComponent(Position.class).y, player.armor.getComponent(Position.class).getDirectionDraw(), newArmorName);
            player.replaceArmor(newArmor);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            log.fatal("Armor not found: " + newArmorClass);
            Loader.exit();
        }
    }

    public static void newGun(Player player) {
        String nowGunName = ((Gun) player.gun).name;

        //Получение валидного имени экиперовки (Которое не равно текущему и соответствует броне и патрону)
        String newGunName;
        boolean end = false;
        String[] allowGun = new ConfigReader(((Armor) player.armor).getConfigFileName()).findString("ALLOW_GUN").split(" ");
        do {
            do {
                newGunName = allowGun[random.nextInt(allowGun.length)];
            } while (newGunName.equals(nowGunName));

            ConfigReader cr = new ConfigReader(Bullet.PATH_SETTING + player.bullet.name + ".properties");
            String[] allowGunForBullet = cr.findString("ALLOW_GUN").split(" ");

            for (int i = 0; i < allowGunForBullet.length; i++) {
                if (newGunName.equals(allowGunForBullet[i])) {
                    end = true;
                }
            }
        } while (!end);

        //Создание класса через рефлексию
        Gun newGun = null;
        try {
            String newGunClassName = new ConfigReader(Gun.PATH_SETTING + newGunName + ".properties").findString("CLASS");
            String newGunFullPath = getClassPackage(player.gun) + "." + newGunClassName;
            newGun = (Gun) Class.forName(newGunFullPath).newInstance();
            newGun.init(player, player.gun.getComponent(Position.class).x, player.gun.getComponent(Position.class).y, player.gun.getComponent(Position.class).getDirectionDraw(), newGunName);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            log.fatal("Gun not found: " + newGunName);
            Loader.exit();
        }

        //Установление новой пушке параметров как у старой
        player.replaceGun(newGun);
    }

    public static void newBullet(Player player) {
        //Получение валидного имени экипировки (Которое не равно текущему и соответствует пушке)
        String newBulletName = "";
        boolean exit;
        do {
            exit = false;

            String config = BULLET_PROPS[random.nextInt(BULLET_PROPS.length)];
            if (!config.contains(".properties")) continue;

            ConfigReader cr = new ConfigReader(Bullet.PATH_SETTING + config);
            if (!contain(cr.findString("ALLOW_GUN").split(" "), ((Gun) player.gun).name)) continue;

            newBulletName = config.substring(0, config.lastIndexOf("."));
            if (!newBulletName.equals(player.bullet.name)) exit = true;
        } while (!exit);

        //Назначение нового патрона
        player.replaceBullet(newBulletName);
    }

    private static String getClassName(Object object) {
        return object.getClass().getSimpleName();
    }

    private static String getClassPackage(Object object) {
        String fullName = object.getClass().getName();
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    private static boolean contain(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) return true;
        }

        return false;
    }

}

