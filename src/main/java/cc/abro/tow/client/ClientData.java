package cc.abro.tow.client;

import cc.abro.orchengine.image.Color;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.factory.MapObjectFactory;
import cc.abro.tow.client.tanks.enemy.Enemy;
import cc.abro.tow.client.tanks.enemy.EnemyBullet;
import cc.abro.tow.client.tanks.player.Player;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

public class ClientData {

    public static String name = "Player";
    public static Color color = Color.WHITE;
    public static Player player;

    public static int peopleMax;
    public static int myIdFromServer; //Мой id на сервере
    public static boolean battle = false; //В текущий момент идет основной процесс игры (бой)
    public static long idNet = 1; //id объекта создаваемого у противника из-за действий игрока

    public static MapObjectFactory mapObjectFactory = new MapObjectFactory();

    //TODO: в наследника Location
    public static Vector<MapObject> mapObjects = new Vector<>();//Список всех объектов на карте
    public static TreeMap<Integer, Enemy> enemy = new TreeMap<>(); //список всех противников
    public static ArrayList<EnemyBullet> enemyBullet; //Список всех патронов противников (EnemyBullet)

    public static String map; //Путь к карте, которая была выбрана (Если ты сервер и выбирал карту)
    public static boolean printStats = false;
    public static boolean printAnalyzerInfo = false;
    public static boolean showGameTabMenu = false;
}
