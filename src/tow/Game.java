package tow;

import java.util.ArrayList;
import java.util.Vector;

import tow.login.LoginWindow;
import tow.map.MapControl;
import tow.net.TCPControl;
import tow.net.TCPMapLoader;
import tow.net.TCPRead;
import tow.net.TCPSend;
import tow.obj.ObjLight;
import tow.player.enemy.EnemyBullet;
import tow.setting.SettingStorage;

public class Game implements Runnable{

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
		new Thread(this).start();
	}
	
	public void run(){
		init();
		
		long nextLoop = System.currentTimeMillis();//Для цикла
		long lastUpdate = System.nanoTime();//Для update
		long startUpdate, startRender;//Для анализатора
		
		while(running){
			if (System.currentTimeMillis() >= nextLoop){
				nextLoop = System.currentTimeMillis() + 1000/Global.setting.MAX_FPS;
				
				startUpdate = System.nanoTime();
				update.loop(System.nanoTime() - lastUpdate);
				lastUpdate = System.nanoTime();
				if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsUpdate(startUpdate);
				
				startRender = System.nanoTime();
				render.loop();
				if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsRender(startRender);
			} else {
				if (!Global.setting.MAX_POWER) 
					try {Thread.sleep(0,1);} catch (InterruptedException e) {}
			}
			
			if (restart) restart();
		}
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
		Global.tcpControl.connect();
		Global.tcpMapLoader.initMap();
		Global.tcpRead.start();
		
		if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) 
			analyzer = new Analyzer();
		
		render.init();
		
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
		Global.setting = new SettingStorage();
		Global.setting.init();
		Global.initSpriteMenu();
		new LoginWindow();
	}
}
