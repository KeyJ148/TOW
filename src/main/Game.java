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

import main.net.LinkCS;
import main.player.enemy.EnemyBullet;

public class Game extends Canvas implements Runnable{
	
	public static final int TPS = 100; //Кол-во повторений update в секунду
	public static final int SKIP_TICKS = 1000/TPS;
	public static final int MAX_FRAME_SKIP = 10;
	public static final boolean console = true;//выводить в консоль сообщения отладки?
	public static final boolean consoleFPS = false;//выводить в консоль фпс?
	public static final boolean monitorFPS = true;//выводить в окно фпс?
	
	public static int WIDTH = 800;
	public static int HEIGHT = 600;//размер окна
	public static String WINDOW_NAME = "Game";

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
		long nextLoops = System.currentTimeMillis();
		int loopsUpdate;
		int loopsRender = 0; //Для подсчёта fps
		int loopsRenderMid = 0;
		int second = 0;
		long fps_t = System.currentTimeMillis(); //Для подсчёта fps
		init();
        
		while(running) { //Главный игровой цикл
			loopsUpdate = 0;
			while ((System.currentTimeMillis() > nextLoops) && (loopsUpdate < MAX_FRAME_SKIP)) {
				update();
				
				nextLoops += SKIP_TICKS;
				loopsUpdate++;
			}
			render();
			loopsRender++;
			if (System.currentTimeMillis() >= fps_t + 1000){
				second++;
				loopsRenderMid += loopsRender;
				if ((Game.consoleFPS) || (Game.monitorFPS)) {
					int objSize = 0;
					for (int i=0;i<Global.obj.size();i++){
						if (Global.obj.get(i) != null){
							objSize++;
						}
					}
					
					int enemySize = 0;
					for (int i=0;i<Global.enemy.length;i++){
						if ((Global.enemy[i] != null) && (!Global.enemy[i].getDestroy())){
							enemySize++;
						}
					}
					
					String strFPS = "FPS: " + loopsRender + "          MidFPS: " + loopsRenderMid/second + "          Object: " + objSize + "          Player: " + (enemySize+1) + "/" + peopleMax;
					if (Game.consoleFPS) System.out.println(strFPS);
					if (Game.monitorFPS) this.monitorStrFPS = strFPS;
				}
				fps_t = System.currentTimeMillis();
				loopsRender = 0;
			}
			if (restart) restart();
		}
	}
	
	//инициализация перед запуском
	public void init() {
		if (Game.console) System.out.println("Inicialization start.");
		Global.obj = new ArrayList<Obj>();
		Global.depth = new ArrayList<DepthVector>();
		Global.enemyBullet = new ArrayList<EnemyBullet>();
		Global.linkCS = new LinkCS();
		
		Global.linkCS.initSprite();
		
		Global.clientThread.initMap(this);
		
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		if (Game.console) System.out.println("Inicialization end.");
	}
	
	public void startRestart(){
		this.restart = true;
	}
	
	public void restart(){
		if (Game.console) System.out.println("Restart map start.");
		
		Global.obj.clear();
		Global.depth.clear();
		Global.enemyBullet.clear();
		Global.obj.trimToSize();
		Global.depth.trimToSize();
		Global.enemyBullet.trimToSize();
		Global.player = null;
		Global.clientSend = null;
		Global.id = 1;
		Global.idNet = 1; 
		for (int i =0; i<Global.enemy.length; i++){
			Global.enemy[i] = null;
		}
		
		Global.clientThread.initMap(this);
		
		this.restart = false;
		if (Game.console) System.out.println("Restart map end.");
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
		if (monitorFPS){
			g.setFont(new Font(null,Font.PLAIN,12));
			g.drawString(monitorStrFPS,1,HEIGHT+9);
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
		
		Global.cameraXView = WIDTH/2;
		Global.cameraYView = HEIGHT/2;
				
		Global.cameraX = cameraShould.getX();
		Global.cameraY = cameraShould.getY();
				
		if (cameraShould.getX() < WIDTH/2){
			Global.cameraX = WIDTH/2;
		}
		if (cameraShould.getY() < HEIGHT/2){
			Global.cameraY = HEIGHT/2;
		}
		if (cameraShould.getX() > widthMap-WIDTH/2){
			Global.cameraX = widthMap-WIDTH/2;
		}
		if (cameraShould.getY() >heightMap-HEIGHT/2){
			Global.cameraY = heightMap-HEIGHT/2;
		}
	}
	
	//Создание окна и запуск игры
	public static void main (String args[]) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JFrame frame = new WindowMain(WINDOW_NAME, game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		
		if (Game.console) System.out.println("Window create.");
	}
}
