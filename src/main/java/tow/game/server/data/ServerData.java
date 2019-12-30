package tow.game.server.data;

import tow.engine3.image.TextureHandler;
import tow.game.server.assistants.BoxCreator;

import java.util.ArrayList;

public class ServerData {

    public static boolean battle = false; //В текущий момент идет основной процесс игры (бой)
    public static int deadPlayerCount = 0;
    public static PlayerData[] playerData;

    public static int widthMap = 0;
    public static int heightMap = 0;
    public static String background = "";
    public static ArrayList<MapObject> map = new ArrayList<>();

    public static BoxCreator boxCreator;

    public static class MapObject{
        public int x, y, direction;
        public TextureHandler textureHandler;
        public String textureName;
    }

}
