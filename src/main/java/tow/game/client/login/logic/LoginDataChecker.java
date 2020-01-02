package tow.game.client.login.logic;

import tow.engine.Global;
import tow.engine.io.logger.Logger;
import tow.engine2.Loader;
import tow.engine.io.logger.AggregateLogger;
import tow.engine.image.Color;

public class LoginDataChecker {

    public static final int MIN_COLOR_VALUE = 125;

    public static void checkName(String name){
        if (name.indexOf(' ') != -1 || name.length() > 20 || name.length() == 0){
            Global.logger.println("Invalid name!", Logger.Type.ERROR);
            Loader.exit();
        }
    }

    public static void checkColor(Color color){
        if (color.getRed() + color.getGreen() + color.getBlue() < MIN_COLOR_VALUE){
            Global.logger.println("Invalid color (Too dark)!", Logger.Type.ERROR);
            Loader.exit();
        }
    }
}
