package main;

import java.util.ArrayList;
import java.util.Vector;

import main.image.DepthVector;
import main.login.LoginWindow;
import main.obj.ObjLight;
import main.player.enemy.EnemyBullet;
import main.setting.SettingStorage;

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
		
		long nextUpdate = System.currentTimeMillis();//Для цикла
		long startUpdate, startRender;//Для анализатора
		
		while(running) { //Главный игровой цикл
			if (System.currentTimeMillis() >= nextUpdate){
				nextUpdate += Global.setting.SKIP_TICKS; 
				startUpdate = System.nanoTime();
				update.loop();
				if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsUpdate(startUpdate);
				
				if (System.currentTimeMillis() < nextUpdate){
					startRender = System.nanoTime();
					render.loop();
					if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsRender(startRender);
				}
				
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
		Global.depth = new ArrayList<DepthVector>();
		Global.enemyBullet = new ArrayList<EnemyBullet>();
		
		Global.initSprite();
		Global.clientThread.initMap(this);
		
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
		Global.depth.clear();
		Global.enemyBullet.clear();
		Global.pingCheck.clear();
		Global.obj.trimToSize();
		Global.depth.trimToSize();
		Global.enemyBullet.trimToSize();
		Global.player = null;
		Global.id = 0;
		Global.idNet = 1; 
		for (int i =0; i<Global.enemy.length; i++){
			Global.enemy[i] = null;
		}
		
		Global.clientThread.initMap(this);
		
		this.restart = false;
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Restart map end.");
	}
	
	//Создание окна логина и запуск игры
	public static void main (String args[]) {
		Global.game = new Game();
		Global.setting = new SettingStorage();
		Global.setting.initFromFile();
		Global.initSpriteMenu();
		new LoginWindow();
	}
}
