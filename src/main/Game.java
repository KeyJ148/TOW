package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.image.DepthVector;
import main.login.WindowMain;
import main.net.LinkCS;
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
				try {
					Thread.sleep(0,1);
				} catch (InterruptedException e) {}
			}
			
			if (restart) restart();
		}
	}
	
	//инициализация перед запуском
	public void init() {
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Inicialization start.");
		
		Global.obj = new ArrayList<ObjLight>();
		Global.depth = new ArrayList<DepthVector>();
		Global.enemyBullet = new ArrayList<EnemyBullet>();
		
		Global.linkCS.initSprite();
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
		Global.id = 1;
		Global.idNet = 1; 
		for (int i =0; i<Global.enemy.length; i++){
			Global.enemy[i] = null;
		}
		
		Global.clientThread.initMap(this);
		
		this.restart = false;
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Restart map end.");
	}
	
	//Создание окна и запуск игры
	public static void main (String args[]) {
		Global.game = new Game();
		Global.setting = new SettingStorage();
		Global.setting.initFromFile();
		Render redner = Global.game.render;
		redner.setPreferredSize(new Dimension(Global.setting.WIDTH_SCREEN, Global.setting.HEIGHT_SCREEN));

		Global.linkCS = new LinkCS();
		Global.linkCS.initSpriteMenu();
		JFrame frame = new WindowMain(Global.setting.WINDOW_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(redner, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		
		//Добавление закрытия на крестик
		frame.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent event) {
				Object[] options = { "Да", "Нет!" };
				int n = JOptionPane.showOptionDialog(event.getWindow(),
						"Закрыть окно?", "Подтверждение",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (n == 0) {
					event.getWindow().setVisible(false);
					System.exit(0);
				}
			}
			
			public void windowOpened(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
		});
		
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Window create.");
	}
}
