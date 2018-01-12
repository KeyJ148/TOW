package game.client.tanks.equipment.bullet;

import engine.setting.ConfigReader;
import game.client.tanks.player.Bullet;

public class BVampire extends Bullet {

    public static final String nameVampire = "BVampire";

    public static double minDamage;
    public static double maxDamage;
    public static double addDamage;
    public static double subDamage;

    public static double nowDamage;

    public static void updateVampire(long delta){
        nowDamage -= subDamage * ((double) delta/1000000000);
        if (nowDamage < minDamage) nowDamage = minDamage;
    }

    public static void hitting(){
        nowDamage += addDamage;
        if (nowDamage > maxDamage) nowDamage = maxDamage;
    }

    public static void setThisBullet(){
        ConfigReader cr = new ConfigReader(PATH_SETTING + "BVampire" + ".properties");
        maxDamage = cr.findDouble("MAX_DAMAGE");
        minDamage = cr.findDouble("MIN_DAMAGE");
        addDamage = cr.findDouble("ADD_DAMAGE");
        subDamage = cr.findDouble("SUB_DAMAGE");

        nowDamage = minDamage;
    }

    @Override
    public void loadData(){
        super.loadData();

        damage += nowDamage;
    }

}

