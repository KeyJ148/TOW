package tow.game.server.data;

import tow.game.client.map.specification.MapSpecification;
import tow.game.server.assistants.BoxCreator;

public class ServerData {

    public static boolean battle = false; //В текущий момент идет основной процесс игры (бой)
    public static int deadPlayerCount = 0;
    public static PlayerData[] playerData;

    public static MapSpecification map;
    public static BoxCreator boxCreator;

}
