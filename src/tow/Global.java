package tow;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import org.newdawn.slick.Color;

import tow.cycle.Game;
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

