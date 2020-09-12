package tech.abro.tow.server.data;

import tech.abro.tow.client.map.specification.MapSpecification;
import tech.abro.tow.server.assistants.BoxCreator;

public class ServerData {

    public static boolean battle = false; //В текущий момент идет основной процесс игры (бой)
    public static int deadPlayerCount = 0;
    public static PlayerData[] playerData;

    public static MapSpecification map;
    public static String mapPath;
    public static BoxCreator boxCreator;

}
