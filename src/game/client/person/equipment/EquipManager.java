package game.client.person.equipment;

import engine.Global;
import engine.io.Logger;
import engine.setting.ConfigReader;
import game.client.ClientData;
import game.client.person.player.Armor;
import game.client.person.player.Bullet;
import game.client.person.player.Gun;
import game.client.person.player.Player;

import java.util.Random;

/*
При добавление необходимо менять...
Броня:
TextureStorage, tow.player.armor, res/settings/armor, res/settings/gun
Оружие:
TextureStorage, tow.player.gun, res/settings/gun, res/settings/armor, res/settings/bullet
Патроны:
TextureStorage, tow.player.bullet, res/settings/bullet, res/settings/gun
*/

public class EquipManager {

    public static Random random = new Random();

    public static void newArmor(Player player){
        String nowArmorName = getClassName(player.armor);

        //Получение валидного имени экипировки (Которое не равно текущему и соответствует пушке)
        String newArmorName;
        do{
            newArmorName = player.gun.allowArmor[random.nextInt(player.gun.allowArmor.length)];
        } while (newArmorName.equals(nowArmorName));

        //Создание класса через рефлексию
        Armor newArmor = null;
        try {
            String newArmorFullPath = getClassPackage(player.armor) + "." + newArmorName;
            newArmor = (Armor) Class.forName(newArmorFullPath).newInstance();
            newArmor.init(player, player.armor.position.x, player.armor.position.y, player.armor.position.getDirectionDraw());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
            Logger.println("Armor not found: " + newArmorName, Logger.Type.ERROR);
            System.exit(0);
        }

        //Устанавливаем новой броне параметры как у текущий брони игрока
        newArmor.movement.setDirection(player.armor.movement.getDirection());
        newArmor.hp = (player.armor.hp/player.armor.hpMax) * newArmor.hpMax; //Устанавливаем эквивалетное здоровье в процентах
        if (player.controller.runUp) newArmor.movement.speed = player.armor.speedTankUp;
        if (player.controller.runDown) newArmor.movement.speed = player.armor.speedTankDown;

        player.armor.destroy();
        player.armor = newArmor;
        Global.room.objAdd(newArmor);
        player.setColorArmor(ClientData.color);

        //Отправляем сообщение о том, что мы сменили броню
        String newName = player.armor.animation[0].name;
        Global.tcpControl.send(19, newName.substring(0, newName.lastIndexOf("_")));
    }

    public static void newGun(Player player){
        String nowGunName = getClassName(player.gun);

        //Получение валидного имени экиперовки (Которое не равно текущему и соответствует броне и патрону)
        String newGunName;
        boolean end = false;
        do{
            do {
                newGunName = player.armor.allowGun[random.nextInt(player.armor.allowGun.length)];
            } while (newGunName.equals(nowGunName));

            ConfigReader cr = new ConfigReader(Bullet.PATH_SETTING + player.bullet.getSimpleName() + ".properties");
            String[] allowGunForBullet = cr.findString("ALLOW_GUN").split(" ");

            for (int i=0;i<allowGunForBullet.length;i++){
                if (newGunName.equals(allowGunForBullet[i])){
                    end = true;
                }
            }
        } while(!end);

        //Создание класса через рефлексию
        Gun newGun = null;
        try {
            String newGunFullPath = getClassPackage(player.gun) + "." + newGunName;
            newGun = (Gun) Class.forName(newGunFullPath).newInstance();
            newGun.init(player, player.gun.position.x, player.gun.position.y, player.gun.position.getDirectionDraw());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
            Logger.println("Gun not found: " + newGunName, Logger.Type.ERROR);
            System.exit(0);
        }

        //Установление новой пушке параметров как у старой
        newGun.position.setDirectionDraw(player.gun.position.getDirectionDraw());

        player.gun.destroy();
        player.gun = newGun;
        Global.room.objAdd(newGun);
        player.setColorGun(ClientData.color);

        //Отправляем сообщение о том, что мы сменили оружие
        Global.tcpControl.send(20, player.gun.texture.name);
    }

    public static void newBullet(Player player){
        String newBulletName;

        //Получение валидного имени экиперовки (Которое не равно текущему и соответствует пушке)
        do{
            newBulletName = player.gun.allowBullet[random.nextInt(player.gun.allowBullet.length)];
        } while (newBulletName.equals(player.bullet));

        //Получение класса через рефлексию
        try {
            String newBulletPackage = player.bullet.getName().substring(0, player.bullet.getName().lastIndexOf("."));
            String newBulletFullPath = newBulletPackage + "." + newBulletName;
            player.bullet = Class.forName(newBulletFullPath);
        } catch (ClassNotFoundException e){
            Logger.println("Bullet not found: " + newBulletName, Logger.Type.ERROR);
            System.exit(0);
        }
    }

    private static String getClassName(Object object){
        return object.getClass().getSimpleName();
    }

    private static String getClassPackage(Object object){
        String fullName = object.getClass().getName();
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

}
