package game.client;

import engine.setting.ConfigReader;

public class GameSetting {

    public static int MPS;
    public static int SOUND_RANGE;
    public static double VAMPIRE_UP_FROM_ONE_DAMAGE;
    public static double VAMPIRE_DOWN_FROM_SEC;
    public static double MIN_BULLET_SPEED_KOEF;


    private static final String SETTING_NAME = "game/main.properties";
    public static void init(){
        ConfigReader cr = new ConfigReader(SETTING_NAME);

        MPS = cr.findInteger("MPS");
        SOUND_RANGE = cr.findInteger("SOUND_RANGE");
        VAMPIRE_UP_FROM_ONE_DAMAGE = cr.findDouble("VAMPIRE_UP_FROM_ONE_DAMAGE");
        VAMPIRE_DOWN_FROM_SEC = cr.findDouble("VAMPIRE_DOWN_FROM_SEC");
        MIN_BULLET_SPEED_KOEF = cr.findDouble("MIN_BULLET_SPEED_KOEF");
    }

}
