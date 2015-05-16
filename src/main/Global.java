package main;

import java.util.ArrayList;

import main.net.ClientNetSend;
import main.net.ClientNetThread;
import main.net.LinkCS;
import main.net.Ping;
import main.player.Player;
import main.player.enemy.Enemy;
import main.player.enemy.EnemyBullet;
import main.setting.SettingStorage;

public class Global {
	
	public static ArrayList<Obj> obj; //Массив со всеми объектами
	public static ArrayList<DepthVector> depth; //Массив с DepthVector
	
	public static Player player; //главный игрок
	public static ClientNetSend clientSend; //цикл отправки данных на сервер
	public static ClientNetThread clientThread; //цикл считывания данных с сервера
	public static LinkCS linkCS; //общее звено клиента и сервера
	
	public static Ping pingCheck;//Объект для проверки пинга
	public static SettingStorage setting;//Объект хранящий основный настройки
	
	public static long id = 1;//Уник. номер следующего объекта
	public static long idNet = 1; //id объекта создаваемого у противника из-за действий игрока  
	
	public static Enemy[] enemy; //список всех противников
	public static ArrayList<EnemyBullet> enemyBullet; //список всех патронов противников (EnemyBullet)
	
	public static double cameraX;
	public static double cameraY;
	public static double cameraXView;
	public static double cameraYView;
	
	public static Sprite background;
	
	public static Sprite road_g;
	public static Sprite road_g_fork;
	public static Sprite road_g_turn;
	public static Sprite road_g_inter;
	public static Sprite road_a;
	public static Sprite road_a_g;
	public static Sprite road_a_fork;
	public static Sprite road_a_inter_big;
	public static Sprite b_default;
	public static Sprite b_mortar;
	public static Sprite b_massgun;
	public static Sprite b_massgun_small;
	public static Sprite b_gunfury;
	public static Sprite defaultgun;
	public static Sprite doublegun;
	public static Sprite gunfury;
	public static Sprite mortar;
	public static Sprite rocketgun;
	public static Sprite massgun;
	public static Sprite player_color;
	public static Sprite home1;
	public static Sprite home2;
	public static Sprite home3;
	public static Sprite home4;
	public static Sprite home5;
	public static Sprite home6;
	public static Sprite home7;
	public static Sprite home8;
	public static Sprite home9;
	public static Sprite home10;
	public static Sprite home11;
	public static Sprite home12;
	public static Sprite home13;
	public static Sprite grayrock1;
	public static Sprite grayrock2;
	public static Sprite grayrock3;
	public static Sprite grayrock4;
	public static Sprite grayrock5;
	public static Sprite grayrock6;
	public static Sprite tree;
	public static Sprite error;
	public static Sprite player_sys;
	
	public static Animation c_default;
	
	//Удаление объекта из массива по id
	public static void delObj(long id){
		for (int i = 0; i<obj.size(); i++){
			if (obj.get(i).getId() == id){
				obj.remove(i);
			}
		}
	}
	
	//Возвращение объекта из массива по id
	public static Obj getObj(long id){
		for (int i = 0; i<obj.size(); i++){
			if (obj.get(i).getId() == id){
				return obj.get(i);
			}
		}
		return null;
	}
}

