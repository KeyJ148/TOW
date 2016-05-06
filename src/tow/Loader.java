package tow;

import java.util.ArrayList;
import java.util.Vector;

import tow.cycle.Analyzer;
import tow.cycle.Game;
import tow.image.TextureManager;
import tow.input.KeyboardHandler;
import tow.input.MouseHandler;
import tow.login.LoginWindow;
import tow.map.Border;
import tow.map.MapControl;
import tow.net.client.TCPControl;
import tow.net.client.TCPMapLoader;
import tow.net.client.TCPRead;
import tow.net.client.TCPSend;
import tow.obj.ObjLight;
import tow.player.enemy.EnemyBullet;
import tow.setting.SettingStorage;

public class Loader {
	

	public static void main (String args[]) {
		//Загрузка библиотек
		try{
			System.loadLibrary("jinput-dx8");
			System.loadLibrary("jinput-raw");
			System.loadLibrary("lwjgl");
			System.loadLibrary("OpenAL32");
			Global.p("32-bit native module load complite");
		} catch (UnsatisfiedLinkError e32){
			try{
				System.loadLibrary("jinput-dx8_64");
				System.loadLibrary("jinput-raw_64");
				System.loadLibrary("lwjgl64");
				System.loadLibrary("OpenAL64");
				Global.p("64-bit native module load complite");
			} catch (UnsatisfiedLinkError e64){
				Global.error("Native module not loading");
				System.exit(0);
			}
		}
		
		init();
		Global.game.run();//Запуск главного цикла
	}
	
	//инициализация перед запуском
	public static void init() {
		Global.setting = new SettingStorage();//Создание хранилища настроек
		Global.setting.init();//Загрузка настроек
		Global.game = new Game();//Создание класса для главного цикла
		
		new LoginWindow();//Создание меню
		while(!Global.game.running) try {Thread.sleep(50);} catch (InterruptedException e) {}//Ожидаем закрытия меню и лобби
		
		Global.game.render.initGL();//Инициализация OpenGL
		
		Global.obj = new Vector<ObjLight>();
		Global.mapControl = new MapControl();
		Global.enemyBullet = new ArrayList<EnemyBullet>();
			
		Global.tcpControl = new TCPControl();
		Global.tcpMapLoader = new TCPMapLoader();
		Global.tcpSend = new TCPSend();
		Global.tcpRead = new TCPRead();
			
		TextureManager.initTexture();
		
		Global.mouseHandler = new MouseHandler();
		Global.keyboardHandler = new KeyboardHandler();
			
		Global.tcpControl.connect();
		Global.tcpMapLoader.initMap();
		Border.createAll();
		Global.tcpRead.start();
			
		if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) 
			Global.game.analyzer = new Analyzer();
			
		if (Global.setting.DEBUG_CONSOLE) Global.p("Inicialization end.");
	}
	
	//Перезапуск карты
	public static void restart(){
		if (Global.setting.DEBUG_CONSOLE) Global.p("Restart map start.");
			
		Global.obj.clear();
		Global.mapControl.clear();
		Global.enemyBullet.clear();
		Global.pingCheck.clear();
		Global.obj.trimToSize();
		Global.enemyBullet.trimToSize();
		Global.player = null;
		Global.id = 0;
		Global.idNet = 1; 
		for (int i =0; i<Global.enemy.length; i++){
			Global.enemy[i] = null;
		}
			
		Global.tcpMapLoader.initMap();
		Border.createAll();
		Global.tcpRead.resumeThread(); 
			
		Global.game.restart = false;
		if (Global.setting.DEBUG_CONSOLE) Global.p("Restart map end.");
	}
}
