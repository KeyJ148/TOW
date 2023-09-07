package cc.abro.tow.client;

import cc.abro.orchengine.context.GameService;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.factory.MapObjectFactory;
import cc.abro.tow.client.tanks.enemy0.EnemyBullet;
import cc.abro.tow.client.tanks.player0.Player;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

@GameService
public class ClientData {

    public Player player;

    public int peopleMax;
    public int myIdFromServer; //Мой id на сервере
    public boolean battle = false; //В текущий момент идет основной процесс игры (бой)
    public long idNet = 1; //id объекта создаваемого у противника из-за действий игрока

    public MapObjectFactory mapObjectFactory = new MapObjectFactory();

    //TODO: в наследника Location
    public Vector<MapObject> mapObjects = new Vector<>();//Список всех объектов на карте //TODO избавиться при переделке сервера
    public TreeMap<Integer, Enemy> enemy = new TreeMap<>(); //список всех противников
    public ArrayList<EnemyBullet> enemyBullet; //Список всех патронов противников (EnemyBullet)

    public boolean printStats = false;
    public boolean printAnalyzerInfo = false;
    public boolean showGameTabMenu = false;
}
