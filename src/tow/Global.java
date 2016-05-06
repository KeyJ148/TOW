package tow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import org.newdawn.slick.Color;

import tow.cycle.Game;
import tow.image.TextureHandler;
import tow.input.KeyboardHandler;
import tow.input.MouseHandler;
import tow.map.MapControl;
import tow.net.client.Ping;
import tow.net.client.TCPControl;
import tow.net.client.TCPMapLoader;
import tow.net.client.TCPRead;
import tow.net.client.TCPSend;
import tow.obj.ObjLight;
import tow.player.Player;
import tow.player.enemy.Enemy;
import tow.player.enemy.EnemyBullet;
import tow.setting.SettingStorage;

public class Global {
	
	public static Game game; //Главный игровой поток
	public static JFrame mainFrame;//Главное окно
	public static MouseHandler mouseHandler;//Обработик мыши
	public static KeyboardHandler keyboardHandler;//Обработик клавиатуры
	
	public static Vector<ObjLight> obj; //Массив со всеми объектами
	public static MapControl mapControl; //Массив со всеми чанками и объектами
	
	public static Player player; //Главный игрок
	
	public static TCPSend tcpSend; //Отправка данных на сервер
	public static TCPRead tcpRead; //Цикл считывания данных с сервера
	public static TCPControl tcpControl; //Хранит настройки и создаёт соединения
	public static TCPMapLoader tcpMapLoader; //Загружает карту и кординаты с сервера
	public static Ping pingCheck;//Объект для проверки пинга
	public static String ip;//ip сервера
	public static int port;//порт сервера
	
	public static SettingStorage setting;//Объект хранящий основный настройки
	
	public static long id = 0;//Уник. номер следующего объекта
	public static long idNet = 1; //id объекта создаваемого у противника из-за действий игрока  
	
	public static Enemy[] enemy; //список всех противников
	public static ArrayList<EnemyBullet> enemyBullet; //Список всех патронов противников (EnemyBullet)
	
	public static String name;//Имя игрока
	public static Color color = new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255));//Цвет игрока
	
	public static int heightMap;
	public static int widthMap;//размер карты
	public static int peopleMax;//кол-во игроков на серве
	
	public static double cameraX;
	public static double cameraY;
	public static double cameraXView;
	public static double cameraYView;
	
	public static TextureHandler background;
	
	public static TextureHandler road_g;
	public static TextureHandler road_g_fork;
	public static TextureHandler road_g_turn;
	public static TextureHandler road_g_inter;
	public static TextureHandler road_a;
	public static TextureHandler road_a_g;
	public static TextureHandler road_a_fork;
	public static TextureHandler road_a_inter_big;
	
	public static TextureHandler b_default;
	public static TextureHandler b_steel;
	public static TextureHandler b_mass;
	public static TextureHandler b_mass_small;
	public static TextureHandler b_square;
	public static TextureHandler b_fury;
	public static TextureHandler b_streamlined; 
	public static TextureHandler b_patron; 
	public static TextureHandler b_vampire; 
	
	public static TextureHandler g_default;
	public static TextureHandler g_double;
	public static TextureHandler g_big;
	public static TextureHandler g_power;
	public static TextureHandler g_fury;
	public static TextureHandler g_mortar;
	public static TextureHandler g_rocketd; 
	public static TextureHandler g_kkp; 
	public static TextureHandler g_sniper; 
	public static TextureHandler g_vampire; 
	
	public static TextureHandler home1;
	public static TextureHandler home2;
	public static TextureHandler home3;
	public static TextureHandler home4;
	public static TextureHandler home5;
	public static TextureHandler home6;
	public static TextureHandler home7;
	public static TextureHandler home8;
	public static TextureHandler home9;
	public static TextureHandler home10;
	public static TextureHandler home11;
	public static TextureHandler home12;
	public static TextureHandler home13;
	public static TextureHandler grayrock1;
	public static TextureHandler grayrock2;
	public static TextureHandler grayrock3;
	public static TextureHandler grayrock4;
	public static TextureHandler grayrock5;
	public static TextureHandler grayrock6;
	public static TextureHandler tree;
	
	public static TextureHandler error;
	public static TextureHandler player_sys;
	public static TextureHandler player_color;
	public static TextureHandler cursor_aim;
	
	public static TextureHandler box_armor;
	public static TextureHandler box_gun;
	public static TextureHandler box_bullet;
	public static TextureHandler box_health;
	
	public static TextureHandler[] a_default;
	public static TextureHandler[] a_fortified;
	public static TextureHandler[] a_elephant;
	public static TextureHandler[] a_fury; 
	public static TextureHandler[] a_mite; 
	public static TextureHandler[] a_vampire; 
	
	//Для функций
	public static final String pathImage = "res/image/";
	public static final String pathAnim = "res/animation/";
	
	//Загрузка всех текстур
	public static void initTexture(){
		Global.road_g = new TextureHandler(pathImage + "Backwall/road_g.png");
		Global.road_g_fork = new TextureHandler(pathImage + "Backwall/road_g_fork.png");
		Global.road_g_turn = new TextureHandler(pathImage + "Backwall/road_g_turn.png");
		Global.road_g_inter = new TextureHandler(pathImage + "Backwall/road_g_inter.png");
		Global.road_a = new TextureHandler(pathImage + "Backwall/road_a.png");
		Global.road_a_g = new TextureHandler(pathImage + "Backwall/road_a_g.png");
		Global.road_a_fork = new TextureHandler(pathImage + "Backwall/road_a_fork.png");
		Global.road_a_inter_big = new TextureHandler(pathImage + "Backwall/road_a_inter_big.png");
			
		Global.b_default = new TextureHandler(pathImage + "Bullet/b_default.png");
		Global.b_steel = new TextureHandler(pathImage + "Bullet/b_steel.png");
		Global.b_square = new TextureHandler(pathImage + "Bullet/b_square.png");
		Global.b_fury = new TextureHandler(pathImage + "Bullet/b_fury.png");
		Global.b_mass = new TextureHandler(pathImage + "Bullet/b_mass.png");
		Global.b_mass_small = new TextureHandler(pathImage + "Bullet/b_mass_small.png");
		Global.b_vampire = new TextureHandler(pathImage + "Bullet/b_vampire.png"); 
		Global.b_patron = new TextureHandler(pathImage + "Bullet/b_patron.png"); 
		Global.b_streamlined = new TextureHandler(pathImage + "Bullet/b_streamlined.png"); 
			
		Global.g_default = new TextureHandler(pathImage + "Gun/g_default.png");
		Global.g_double = new TextureHandler(pathImage + "Gun/g_double.png");
		Global.g_fury = new TextureHandler(pathImage + "Gun/g_fury.png");
		Global.g_mortar = new TextureHandler(pathImage + "Gun/g_mortar.png");
		Global.g_rocketd = new TextureHandler(pathImage + "Gun/g_rocketd.png"); 
		Global.g_big = new TextureHandler(pathImage + "Gun/g_mass.png");
		Global.g_power = new TextureHandler(pathImage + "Gun/g_power.png");
		Global.g_kkp = new TextureHandler(pathImage + "Gun/g_kkp.png"); 
		Global.g_sniper = new TextureHandler(pathImage + "Gun/g_sniper.png"); 
		Global.g_vampire = new TextureHandler(pathImage + "Gun/g_vampire.png"); 
		
		Global.player_color = new TextureHandler(pathImage + "Sys/player_color.png");
		Global.player_sys = new TextureHandler(pathImage + "Sys/player_sys.png");
		Global.error = new TextureHandler(pathImage + "Sys/error.png");
		Global.cursor_aim = new TextureHandler(pathImage + "Sys/cursor_aim.png");
	
		Global.home1 = new TextureHandler(pathImage + "Wall/home1.png");
		Global.home2 = new TextureHandler(pathImage + "Wall/home2.png");
		Global.home3 = new TextureHandler(pathImage + "Wall/home3.png");
		Global.home4 = new TextureHandler(pathImage + "Wall/home4.png");
		Global.home5 = new TextureHandler(pathImage + "Wall/home5.png");
		Global.home6 = new TextureHandler(pathImage + "Wall/home6.png");
		Global.home7 = new TextureHandler(pathImage + "Wall/home7.png");
		Global.home8 = new TextureHandler(pathImage + "Wall/home8.png");
		Global.home9 = new TextureHandler(pathImage + "Wall/home9.png");
		Global.home10 = new TextureHandler(pathImage + "Wall/home10.png");
		Global.home11 = new TextureHandler(pathImage + "Wall/home11.png");
		Global.home12 = new TextureHandler(pathImage + "Wall/home12.png");
		Global.home13 = new TextureHandler(pathImage + "Wall/home13.png");
		Global.grayrock1 = new TextureHandler(pathImage + "Wall/grayrock1.png");
		Global.grayrock2 = new TextureHandler(pathImage + "Wall/grayrock2.png");
		Global.grayrock3 = new TextureHandler(pathImage + "Wall/grayrock3.png");
		Global.grayrock4 = new TextureHandler(pathImage + "Wall/grayrock4.png");
		Global.grayrock5 = new TextureHandler(pathImage + "Wall/grayrock5.png");
		Global.grayrock6 = new TextureHandler(pathImage + "Wall/grayrock6.png");
		Global.tree = new TextureHandler(pathImage + "Wall/tree.png");
		
		Global.box_armor = new TextureHandler(pathImage + "Gaming/box_armor.png");
		Global.box_gun = new TextureHandler(pathImage + "Gaming/box_gun.png");
		Global.box_bullet = new TextureHandler(pathImage + "Gaming/box_bullet.png");
		Global.box_health = new TextureHandler(pathImage + "Gaming/box_health.png");
		
		Global.a_default = parseAnimation(pathAnim + "Corps/a_default");
		Global.a_fortified = parseAnimation(pathAnim + "Corps/a_fortified");
		Global.a_elephant = parseAnimation(pathAnim + "Corps/a_elephant");
		Global.a_fury = parseAnimation(pathAnim + "Corps/a_fury"); 
		Global.a_mite = parseAnimation(pathAnim + "Corps/a_mite"); 
		Global.a_vampire = parseAnimation(pathAnim + "Corps/a_vampire");
	}
	
	public static TextureHandler[] parseAnimation(String path){
		int n=0;
		while (new File(path + "/" + (n+1) + ".png").exists()){
			n++;
		}
		
		TextureHandler[] textureHandler = new TextureHandler[n];
		
		//Загрузка изображений
		for(int i=0;i<n;i++){
			textureHandler[i] = new TextureHandler(path + "/" + (i+1) + ".png");
		}
		
		return textureHandler;
	}
	
	//Получение ссылки на спрайт из строки
	public static TextureHandler getSprite(String s){
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
			case "b_mortar": return Global.b_square;
			case "b_fury": return Global.b_fury;
			case "b_mass": return Global.b_mass;
			case "b_mass_small": return Global.b_mass_small;
			case "b_patron": return Global.b_patron; 
			case "b_vampire": return Global.b_vampire; 
			case "b_streamlined": return Global.b_streamlined; 
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
			case "b_vampire": return "Bullet"; 
			case "b_patron": return "Bullet"; 
			case "b_streamlined": return "Bullet"; 
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

