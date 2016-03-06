package tow;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import tow.image.Animation;
import tow.image.Sprite;
import tow.map.MapControl;
import tow.net.Ping;
import tow.net.TCPControl;
import tow.net.TCPMapLoader;
import tow.net.TCPRead;
import tow.net.TCPSend;
import tow.obj.ObjLight;
import tow.player.Player;
import tow.player.enemy.Enemy;
import tow.player.enemy.EnemyBullet;
import tow.setting.SettingStorage;

public class Global {
	
	public static Game game; //Главный игровой поток
	public static JFrame mainFrame;//Главное окно
	
	public static Vector<ObjLight> obj; //Массив со всеми объектами
	public static MapControl mapControl; //Массив со всеми чанками и объектами
	
	public static Player player; //Главный игрок
	public static int playerId; //Уникальный id для общения с сервером
	
	public static TCPSend tcpSend; //Отправка данных на сервер
	public static TCPRead tcpRead; //Цикл считывания данных с сервера
	public static TCPControl tcpControl; //Хранит настройки и создаёт соединения
	public static TCPMapLoader tcpMapLoader; //Загружает карту и кординаты с сервера
	public static Ping pingCheck;//Объект для проверки пинга
	public static String ip;//ip сервера
	public static int port;//порт сервера
	
	public static SettingStorage setting;//Объект хранящий основный настройки
	
	public static long idNext = 0;//Уник. номер следующего объекта
	public static long idNet = 1; //id объекта создаваемого у противника из-за действий игрока  
	
	public static Enemy[] enemy; //список всех противников
	public static ArrayList<EnemyBullet> enemyBullet; //Список всех патронов противников (EnemyBullet)
	
	public static String name;//Имя игрока
	public static Color color = new Color((int) (Math.random()*Integer.MAX_VALUE));//Цвет игрока
	
	public static int heightMap;
	public static int widthMap;//размер карты
	public static int peopleMax;//кол-во игроков на серве
	
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
	public static Sprite b_square;
	public static Sprite b_mass;
	public static Sprite b_mass_small;
	public static Sprite b_fury;
	public static Sprite b_steel;
	public static Sprite b_streamlined; //
	public static Sprite b_patron; //
	public static Sprite b_vampire; //
	
	public static Sprite g_default;
	public static Sprite g_double;
	public static Sprite g_fury;
	public static Sprite g_mortar;
	public static Sprite g_rocketd; //
	public static Sprite g_big;
	public static Sprite g_power;
	public static Sprite g_kkp; //
	public static Sprite g_sniper; //
	public static Sprite g_vampire; //
	
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
	public static Sprite player_color;
	public static Sprite cursor_aim;
	
	public static Sprite box_armor;
	public static Sprite box_gun;
	public static Sprite box_bullet;
	public static Sprite box_health;
	
	public static Animation a_default;
	public static Animation a_fortified;
	public static Animation a_elephant;
	public static Animation a_fure; //
	public static Animation a_mite; //
	public static Animation a_vampire; //
	
	//Для функций
	public static final String pathImage = "res/image/";
	public static final String pathAnim = "res/animation/";
	
	//Загрузка всех спрайтов
	public static void initSprite(){
		
		Global.road_g = new Sprite(pathImage + "Backwall/road_g.png");
		Global.road_g_fork = new Sprite(pathImage + "Backwall/road_g_fork.png");
		Global.road_g_turn = new Sprite(pathImage + "Backwall/road_g_turn.png");
		Global.road_g_inter = new Sprite(pathImage + "Backwall/road_g_inter.png");
		Global.road_a = new Sprite(pathImage + "Backwall/road_a.png");
		Global.road_a_g = new Sprite(pathImage + "Backwall/road_a_g.png");
		Global.road_a_fork = new Sprite(pathImage + "Backwall/road_a_fork.png");
		Global.road_a_inter_big = new Sprite(pathImage + "Backwall/road_a_inter_big.png");
		
		Global.b_default = new Sprite(pathImage + "Bullet/b_default.png");
		Global.b_steel = new Sprite(pathImage + "Bullet/b_steel.png");
		Global.b_mortar = new Sprite(pathImage + "Bullet/b_mortar.png");
		Global.b_fury = new Sprite(pathImage + "Bullet/b_fury.png");
		Global.b_mass = new Sprite(pathImage + "Bullet/b_mass.png");
		Global.b_mass_small = new Sprite(pathImage + "Bullet/b_mass_small.png");
		Global.b_vampire = new Sprite(pathImage + "Bullet/b_vampire.png"); //
		Global.b_patron = new Sprite(pathImage + "Bullet/b_patron.png"); //
		Global.b_streamlined = new Sprite(pathImage + "Bullet/b_streamlined.png"); //
		
		Global.g_default = new Sprite(pathImage + "Gun/g_default.png");
		Global.g_double = new Sprite(pathImage + "Gun/g_double.png");
		Global.g_fury = new Sprite(pathImage + "Gun/g_fury.png");
		Global.g_mortar = new Sprite(pathImage + "Gun/g_mortar.png");
		Global.g_rocketd = new Sprite(pathImage + "Gun/g_rocketd.png"); //
		Global.g_big = new Sprite(pathImage + "Gun/g_mass.png");
		Global.g_power = new Sprite(pathImage + "Gun/g_power.png");
		Global.g_kkp = new Sprite(pathImage + "Gun/g_kkp.png"); //
		Global.g_sniper = new Sprite(pathImage + "Gun/g_sniper.png"); //
		Global.g_vampire = new Sprite(pathImage + "Gun/g_vampire.png"); //
		
		Global.player_color = new Sprite(pathImage + "Sys/player_color.png");
		Global.player_sys = new Sprite(pathImage + "Sys/player_sys.png");
		Global.error = new Sprite(pathImage + "Sys/error.png");
		Global.cursor_aim = new Sprite(pathImage + "Sys/cursor_aim.png");
		
		Global.home1 = new Sprite(pathImage + "Wall/home1.png");
		Global.home2 = new Sprite(pathImage + "Wall/home2.png");
		Global.home3 = new Sprite(pathImage + "Wall/home3.png");
		Global.home4 = new Sprite(pathImage + "Wall/home4.png");
		Global.home5 = new Sprite(pathImage + "Wall/home5.png");
		Global.home6 = new Sprite(pathImage + "Wall/home6.png");
		Global.home7 = new Sprite(pathImage + "Wall/home7.png");
		Global.home8 = new Sprite(pathImage + "Wall/home8.png");
		Global.home9 = new Sprite(pathImage + "Wall/home9.png");
		Global.home10 = new Sprite(pathImage + "Wall/home10.png");
		Global.home11 = new Sprite(pathImage + "Wall/home11.png");
		Global.home12 = new Sprite(pathImage + "Wall/home12.png");
		Global.home13 = new Sprite(pathImage + "Wall/home13.png");
		Global.grayrock1 = new Sprite(pathImage + "Wall/grayrock1.png");
		Global.grayrock2 = new Sprite(pathImage + "Wall/grayrock2.png");
		Global.grayrock3 = new Sprite(pathImage + "Wall/grayrock3.png");
		Global.grayrock4 = new Sprite(pathImage + "Wall/grayrock4.png");
		Global.grayrock5 = new Sprite(pathImage + "Wall/grayrock5.png");
		Global.grayrock6 = new Sprite(pathImage + "Wall/grayrock6.png");
		Global.tree = new Sprite(pathImage + "Wall/tree.png");
		
		Global.box_armor = new Sprite(pathImage + "Gaming/box_armor.png");
		Global.box_gun = new Sprite(pathImage + "Gaming/box_gun.png");
		Global.box_bullet = new Sprite(pathImage + "Gaming/box_bullet.png");
		Global.box_health = new Sprite(pathImage + "Gaming/box_health.png");
		
		Global.a_default = new Animation(pathAnim + "Corps/a_default",0,2);
		Global.a_fortified = new Animation(pathAnim + "Corps/a_fortified",0,2);
		Global.a_elephant = new Animation(pathAnim + "Corps/a_elephant",0,1);
		Global.a_fury = new Animation(pathAnim + "Corps/a_fury",0,2); //
		Global.a_mite = new Animation(pathAnim + "Corps/a_mite",0,2); //
		Global.a_vampire = new Animation(pathAnim + "Corps/a_vampire",0,2); //
	}
	
	//Загрузка спрайтов для меню
	public static void initSpriteMenu(){
		Global.player_color = new Sprite(pathImage + "Sys/player_color.png");
	}
	
	//Получение ссылки на спрайт из строки
	public static Sprite getSprite(String s){
		switch(s){
			case "home1": return Global.home1;
			case "home2": return Global.home2;
			case "home3": return Global.home3;
			case "home4": return Global.home4;
			case "home5": return Global.home5;
			case "home6": return Global.home6;
			case "home7": return Global.home7;
			case "home8": return Global.home8;
			case "home9": return Global.home9;
			case "home10": return Global.home10;
			case "home11": return Global.home11;
			case "home12": return Global.home12;
			case "home13": return Global.home13;
			
			case "road_g": return Global.road_g;
			case "road_g_fork": return Global.road_g_fork;
			case "road_g_turn": return Global.road_g_turn;
			case "road_g_inter": return Global.road_g_inter;
			case "road_a": return Global.road_a;
			case "road_a_g": return Global.road_a_g;
			case "road_a_fork": return Global.road_a_fork;
			case "road_a_inter_big": return Global.road_a_inter_big;
			
			case "player_color": return Global.player_color;
			
			case "b_default": return Global.b_default;
			case "b_steel": return Global.b_steel;
			case "b_mortar": return Global.b_mortar;
			case "b_fury": return Global.b_fury;
			case "b_mass": return Global.b_mass;
			case "b_mass_small": return Global.b_mass_small;
			case "b_patron": return Global.b_patron; //
			case "b_vampire": return Global.b_vampire; //
			case "b_streamlined": return Global.b_streamlined; //
		}
		return Global.error;
	}
	
	public static String getType(String s){
		switch(s){
			case "home1": return "Home";
			case "home2": return "Home";
			case "home3": return "Home";
			case "home4": return "Home";
			case "home5": return "Home";
			case "home6": return "Home";
			case "home7": return "Home";
			case "home8": return "Home";
			case "home9": return "Home";
			case "home10": return "Home";
			case "home11": return "Home";
			case "home12": return "Home";
			case "home13": return "Home";
			
			case "road_g": return "Road";
			case "road_g_fork": return "Road";
			case "road_g_turn": return "Road";
			case "road_g_inter": return "Road";
			case "road_a": return "Road";
			case "road_a_g": return "Road";
			case "road_a_fork": return "Road";
			case "road_a_inter_big": return "Road";
			
			case "b_default": return "Bullet";
			case "b_steel": return "Bullet";
			case "b_mortar": return "Bullet";
			case "b_fury": return "Bullet";
			case "b_mass": return "Bullet";
			case "b_mass_small": return "Bullet";
			case "b_vampire": return "Bullet"; //
			case "b_patron": return "Bullet"; //
			case "b_streamlined": return "Bullet"; //
		}
		return "Error";
	}
	
	//Разбиение строки по пробелам
	public static String parsString(String s,int numFind){
		int numWord = 0;
		int numSpace = -1;
		int k = 0;
		do{
			if (s.charAt(k) == ' '){
				numWord++;
				if (numWord != numFind){
					numSpace = k;
				} else {
					return s.substring(numSpace+1,k);
				}
			}
			if (k == s.length()-1){
				return s.substring(numSpace+1,k+1);
			}
			k++;
		}while(true);
	}
	
	//Добавление объекта в массив по id
	public static void addObj(ObjLight objLight){
		obj.add((int) objLight.getId(), objLight);
		mapControl.add(objLight);
	}
	
	//Удаление объекта из массива по id
	public static void delObj(long id){
		mapControl.del((int) id);//Используется Global.obj, так что должно быть раньше
		obj.set((int) id, null);
	}
	
	//Возвращение объекта из массива по id
	public static ObjLight getObj(long id){
		return obj.get((int) id);
	}
	
	//Возвращение кол-ва объектов
	public static int getSize(){
		return obj.size();
	}
	
	//Вывод сообщения о ошибке
	public static void error(String s){
		System.out.println("[ERROR] " + s);
	}
	
	//Вывод информационного сообщения
	public static void p(String s){
		System.out.println("[INFO] " + s);
	}
	
	public static void p(){
		Global.p("ALARM");
	}
	
	//Вывод сообщения  файл
	public static PrintWriter out;
	public static void log(String s){
		if (out == null){
			try {
				out = new PrintWriter(new FileWriter("res/log.txt"));
			} catch (IOException e) {
				Global.error("Write log file");
			}
		}
		out.println(s);
	}
}

