package cc.abro.tow.client;

import cc.abro.orchengine.context.GameService;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.Box;
import cc.abro.tow.client.tanks.enemy.EnemyTank;
import cc.abro.tow.client.tanks.equipment.bullet0.EnemyBullet;
import cc.abro.tow.client.tanks.player.PlayerTank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@GameService
public class ClientData {

    public PlayerTank player;

    public int peopleMax;
    public int myIdFromServer; //Мой id на сервере
    public boolean battle = false; //В текущий момент идет основной процесс игры (бой)
    public long idNet = 1; //id объекта создаваемого у противника из-за действий игрока

    public Vector<MapObject> mapObjects = new Vector<>();//Список всех объектов на карте
    public Map<Integer, EnemyTank> enemy = new HashMap<>(); //список всех противников
    public int lastDamageDealerEnemyId = -1;
    public ArrayList<EnemyBullet> enemyBullet; //Список всех патронов противников (EnemyBullet)
    public ArrayList<Box> boxes = new ArrayList<>();

    public boolean printAnalyzerInfo = false;
    public boolean showGameTabMenu = false;
}
