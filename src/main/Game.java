package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;

import main.login.WindowMain;
import main.net.LinkCS;
import main.player.enemy.EnemyBullet;
import main.setting.SettingStorage;

public class Game extends Canvas implements Runnable{

	public Analyzer analyzer;//Вывод отладочных данных
	
	public boolean running = false; //Выполнение главного игрового цикла
	public boolean restart = false; //Перезагрузка карты
	
	public String monitorStrFPS = "";
	
	public String name;//имя персонажа
	public DataInputStream in;//данные сетевой игры
	public DataOutputStream out;
	
	public int heightMap;
	public int widthMap;//размер карты
	public int peopleMax;//кол-во игроков на серве
	
	private static final long serialVersionUID = 1L; //Синхронизация
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void run(){
		init();
		
		long nextLoops = System.currentTimeMillis();//Для цикла
		while(running) { //Главный игровой цикл
			if (System.currentTimeMillis() >= nextLoops){
				nextLoops += Global.setting.SKIP_TICKS; 
				update();
				render();
				if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) 
					analyzer.loops();
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
		
		Global.obj = new ArrayList<Obj>();
		Global.depth = new ArrayList<DepthVector>();
		Global.enemyBullet = new ArrayList<EnemyBullet>();
		Global.linkCS = new LinkCS();
		
		Global.linkCS.initSprite();
		Global.clientThread.initMap(this);
		
		if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) 
			analyzer = new Analyzer(this);
		
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Inicialization end.");
	}
	
	public void startRestart(){
		this.restart = true;
	}
	
	public void restart(){
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Restart map start.");
		//Global.clientThread.stopThread();
		
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
    
    //Отрисовка экрана (с частотой fps)
	public void render() {
		//Включение двойной буферизации
		BufferStrategy bs = getBufferStrategy(); 
		if (bs == null) {
			createBufferStrategy(2);
			requestFocus();
			return;
		}
        //Настройки окна
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(new Color(24,116,0));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//заливка фона
		Image back = Global.background.getImage();
		int dxf,dyf;
		for (int dy = 0; dy<=this.heightMap; dy+=64){
			for (int dx = 0; dx<=this.widthMap; dx+=64){
				dxf = (int) Math.round(Global.cameraXView - (Global.cameraX - dx));
				dyf = (int) Math.round( Global.cameraYView - (Global.cameraY - dy));
				g.drawImage(back,dxf,dyf,null);
			}
		}
		
		//Сортировка массивов по глубине объектов в них
		for (int i=0; i<Global.depth.size(); i++){
			for (int j=0; j<Global.depth.size()-1; j++){
				DepthVector dv1 = (DepthVector) Global.depth.get(j);
				DepthVector dv2 = (DepthVector) Global.depth.get(j+1);
				if (dv1.depth < dv2.depth){
					Global.depth.set(j, dv2);
					Global.depth.set(j+1, dv1);
				}
			}
		}
		
		//Отрисовка объектов		
		for (int i=0; i<Global.depth.size(); i++){
			DepthVector dv = (DepthVector) Global.depth.get(i);
			for (int j=0; j<dv.number.size(); j++){
				if (Global.obj.get(i) != null){
					try{
						Obj obj = (Obj) Global.getObj((long) dv.number.get(j));
						obj.draw(g);
					}catch (NullPointerException e){
						System.out.println("[ERROR] Draw object null");
					}
				}
			}
		}
		
		//Отрисвока надписей
		AffineTransform at = new AffineTransform(); 
		g.setTransform(at);
		g.setColor(new Color(0,0,0));
		g.setFont(new Font(null,Font.PLAIN,12));
		if (!Global.player.getDestroy()){
			g.drawString(name,Global.player.nameX,Global.player.nameY);
		}
		for(int i = 0;i<Global.enemy.length;i++){
			try{
				if (!Global.enemy[i].getDestroy()){
					g.drawString(Global.enemy[i].name,Global.enemy[i].nameX,Global.enemy[i].nameY);
				}
			}catch(NullPointerException e){}
		}
		g.setFont(new Font(null,Font.BOLD,20));
		if (!Global.player.getDestroy()){
			long hp = Math.round(Global.player.getArmor().getHp());
			long hpMax = Math.round(Global.player.getArmor().getHpMax());
			g.drawString("HP: " + hp + "/" + hpMax,1,16);
		}
		if (Global.setting.DEBUG_MONITOR_FPS){
			g.setFont(new Font(null,Font.PLAIN,12));
			g.drawString(monitorStrFPS,1,Global.setting.HEIGHT+9);
		}
		
		//Магия [ON]
		g.dispose();
		bs.show();
		//Магия [OFF]
	}
    
    //Обновление объектов (TPS)
	public void update(){
		for (int i=0; i<Global.obj.size(); i++){
			if (Global.obj.get(i) != null){
				Obj obj = (Obj) Global.obj.get(i);
				obj.update();
			}
		}
		
		Obj cameraShould = (Obj) Global.player;//Объект, за которым следует камера
		if (Global.player.getDestroy()){
			for(int i = 0;i<Global.enemy.length;i++){
				if (!Global.enemy[i].getDestroy()){
					cameraShould = (Obj) Global.enemy[i];
					break;
				}
			}
		}
		
		int width = Global.setting.WIDTH;
		int height = Global.setting.HEIGHT;
		
		Global.cameraXView = width/2;
		Global.cameraYView = height/2;
				
		Global.cameraX = cameraShould.getX();
		Global.cameraY = cameraShould.getY();
				
		if (cameraShould.getX() < width/2){
			Global.cameraX = width/2;
		}
		if (cameraShould.getY() < height/2){
			Global.cameraY = height/2;
		}
		if (cameraShould.getX() > widthMap-width/2){
			Global.cameraX = widthMap-width/2;
		}
		if (cameraShould.getY() >heightMap-height/2){
			Global.cameraY = heightMap-height/2;
		}
	}
	
	//Создание окна и запуск игры
	public static void main (String args[]) {
		Game game = new Game();
		Global.setting = new SettingStorage();
		Global.setting.initFromFile();
		game.setPreferredSize(new Dimension(Global.setting.WIDTH, Global.setting.HEIGHT));

		JFrame frame = new WindowMain(Global.setting.WINDOW_NAME, game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Window create.");
	}
}
