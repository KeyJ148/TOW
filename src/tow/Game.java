package tow;

import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.opengl.Display;

import tow.login.LoginWindow;
import tow.map.MapControl;
import tow.net.client.TCPControl;
import tow.net.client.TCPMapLoader;
import tow.net.client.TCPRead;
import tow.net.client.TCPSend;
import tow.obj.ObjLight;
import tow.player.enemy.EnemyBullet;
import tow.setting.SettingStorage;

public class Game{

	public Update update;
	public Render render;
	public Analyzer analyzer;//Вывод отладочных данных
	
	public boolean running = false; //Выполнение главного игрового цикла
	public boolean restart = false; //Перезагрузка карты
	
	public Game(){
		update = new Update();
		render = new Render();
	}
	
	public void start() {
		running = true;
	}
	
	public void run(){
		while(!running) try {Thread.sleep(50);} catch (InterruptedException e) {}
		
		init();
		
		long lastUpdate = System.nanoTime();//Для update
		long startUpdate, startRender;//Для анализатора
		
		while(!Display.isCloseRequested()){
				
			startUpdate = System.nanoTime();
			update.loop(System.nanoTime() - lastUpdate);
			lastUpdate = System.nanoTime();
			if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsUpdate(startUpdate);
				
			startRender = System.nanoTime();
			render.loop();
			if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsRender(startRender);
			if (restart) restart();
		}
		
		System.exit(0);
	}
	
	//инициализация перед запуском
	public void init() {
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Inicialization start.");
		
		Global.obj = new Vector<ObjLight>();
		Global.mapControl = new MapControl();
		Global.enemyBullet = new ArrayList<EnemyBullet>();
		
		Global.tcpControl = new TCPControl();
		Global.tcpMapLoader = new TCPMapLoader();
		Global.tcpSend = new TCPSend();
		Global.tcpRead = new TCPRead();
		
		Global.initSprite();
		Global.mouseHandler = new MouseHandler();
		Global.keyboardHandler = new KeyboardHandler();
		
		Global.tcpControl.connect();
		Global.tcpMapLoader.initMap();
		Global.tcpRead.start();
		
		if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) 
			analyzer = new Analyzer();
		
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Inicialization end.");
	}
	
	public void startRestart(){
		this.restart = true;
	}
	
	//Перезапуск карты
	public void restart(){
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Restart map start.");
		
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
		Global.tcpRead.resumeThread();
		
		this.restart = false;
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Restart map end.");
	}
	
	//Создание окна логина и запуск игры
	public static void main (String args[]) {
		Global.game = new Game();
		
		Global.game.render.init();
		Global.setting = new SettingStorage();
		Global.setting.init();
		new LoginWindow();
		Global.game.run();
	}
}
