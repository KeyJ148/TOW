package cc.abro.tow.client;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.image.Color;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.factory.MapObjectFactory;
import cc.abro.tow.client.tanks.enemy.Enemy;
import cc.abro.tow.client.tanks.enemy.EnemyBullet;
import cc.abro.tow.client.tanks.player.Player;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

@GameService
public class ClientData {

    public String name = "Player";  //TODO возможно убрать и использовать из SettingsService (и color)
    public Color color = Color.WHITE;
    public Player player;

    public double musicVolume;
    public double soundVolume;

    public int peopleMax;
    public int myIdFromServer; //Мой id на сервере
    public boolean battle = false; //В текущий момент идет основной процесс игры (бой)
    public long idNet = 1; //id объекта создаваемого у противника из-за действий игрока

    public MapObjectFactory mapObjectFactory = new MapObjectFactory();

    //TODO: в наследника Location
    public Vector<MapObject> mapObjects = new Vector<>();//Список всех объектов на карте
    public TreeMap<Integer, Enemy> enemy = new TreeMap<>(); //список всех противников
    public ArrayList<EnemyBullet> enemyBullet; //Список всех патронов противников (EnemyBullet)

    public String map; //Путь к карте, которая была выбрана (Если ты сервер и выбирал карту)
    public boolean printStats = false;
    public boolean printAnalyzerInfo = false;
    public boolean showGameTabMenu = false;
}
