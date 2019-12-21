package tow.game.client.login.logic;

import tow.engine2.Loader;
import tow.engine2.io.Logger;
import java.awt.Color;

public class LoginDataChecker {

    public static final int MIN_COLOR_VALUE = 125;

    public static void checkName(String name){
        if (name.indexOf(' ') != -1 || name.length() > 20 || name.length() == 0){
            Logger.println("Invalid name!", Logger.Type.ERROR);
            Loader.exit();
        }
    }

    public static void checkColor(Color color){
        if (color.getRed() + color.getGreen() + color.getBlue() < MIN_COLOR_VALUE){
            Logger.println("Invalid color (Too dark)!", Logger.Type.ERROR);
            Loader.exit();
        }
    }
}
