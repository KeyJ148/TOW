package main.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import main.Game;
import main.Global;
import main.Sprite;
import main.home.Home;
import main.home.Road;
import main.player.Player;
import main.player.enemy.Enemy;
import main.player.enemy.EnemyBullet;

public class ClientNetThread extends Thread{
	
	public DataInputStream in;
	public DataOutputStream out;
	public Socket sock;
	public Game game;
	
	public boolean takeMessage = true;
	
	public volatile int sizeData = 0; //bytes
	public Object sizeDataMonitor;
	
	public ClientNetThread(DataInputStream in, DataOutputStream out, Socket sock){
		this.in = in;
		this.out = out;
		this.sock = sock;
		this.sizeDataMonitor = new Object();
	}
	
	public void initMap(Game game){
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Download map start.");
		this.game = game;
		
		//Скачивание карты
		String s = "";
		try{
			
			if (game.restart){
				this.out.writeUTF("-1 ");
			}
			
			if (Global.setting.DEBUG_CONSOLE) System.out.println("Wait loading map.");
			while(true){
				s = this.in.readUTF();
				if (s.equals("6 ")) break;
			}
			
			//Разрешение карты(для камеры)
			s = downloadMap();
			if (Global.setting.DEBUG_CONSOLE) System.out.println("Download map size complite.");
		} catch(IOException e){
			Global.error("Download map size");
			System.exit(0);
		}
		game.widthMap = Integer.parseInt(Global.linkCS.parsString(s,1));
		game.heightMap = Integer.parseInt(Global.linkCS.parsString(s,2));
		switch (Integer.parseInt(Global.linkCS.parsString(s,3))){
			case 0: Global.background = new Sprite("image/Background/grass.png"); break;
			case 1: Global.background = new Sprite("image/Background/sand.png"); break;
			case 2: Global.background = new Sprite("image/Background/snow.png"); break;
		}
		//Загрузка объектов
		int x,y,direction;
		String sprite,name;
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Download map object start.");
		while(true){
			s = downloadMap();
			
			if (s.equals("8 ")){
				if (Global.setting.DEBUG_CONSOLE) System.out.println("Download map object complite.");
				break;
			}
			x = Integer.parseInt(Global.linkCS.parsString(s,1));
			y = Integer.parseInt(Global.linkCS.parsString(s,2));
			direction = Integer.parseInt(Global.linkCS.parsString(s,3));
			sprite = Global.linkCS.parsString(s,4);
			name = Global.linkCS.parsString(s,5);
			switch(name){
				case "Home": new Home(x,y,direction,Global.linkCS.getSprite(sprite),game); break;
				case "Road": new Road(x,y,direction,Global.linkCS.getSprite(sprite),game); break;
			}
		}
		genTank();
	}
	
	//получение данных
	public void genTank(){
		try{
			this.out.writeUTF("-3 " + game.name);//отправка имени
			String s = downloadMap();//получение кор танка
			double x = (double) Integer.parseInt(s.substring(0,s.indexOf(' ')));
			double y = (double) Integer.parseInt(s.substring(s.indexOf(' ')+1));
			s = downloadMap();//получение кол-во игрков
			this.game.peopleMax = Integer.parseInt(s.substring(0,s.indexOf(' ')));
			Global.enemy = new Enemy[this.game.peopleMax-1];
			Global.player = new Player(x,y,Math.random()*360,game);
			
			if (Global.setting.DEBUG_CONSOLE) System.out.println("Generation tank complite.");
		} catch(IOException e){
			Global.error("Message about generation tank");
			System.exit(0);
		}
		
		new ClientNetSend(this.out, this.game);
		if (game.restart){
			startThread();
		} else {
			start();
		}
	}
	
	public String downloadMap(){
		try {
			String s;
			do {
				s = this.in.readUTF();
				if (Integer.parseInt(Global.linkCS.parsString(s,1)) == 8){
					return s;
				}
			}while(Integer.parseInt(Global.linkCS.parsString(s,1)) != 7);
			return s.substring(2);
		} catch (IOException e) {
			Global.error("Method for download map");
			return "";
		}
	}
	
	public void run(){
		//постоянный обмен данными
		//на TCP
		takeMessage = true;//Принимать сообщения?
		String str;
		try{
			
			while(true){
				if (takeMessage){
					str = this.in.readUTF();
					synchronized (sizeDataMonitor){
						sizeData += str.length()*2;
					}
					if (!takeMessage) break;
					switch (Integer.parseInt(Global.linkCS.parsString(str,1))){
						case 0: take0(str); break;
						case 1: take1(str); break;
						case 2: take2(str); break;
						case 3: take3(str); break;
						case 4: take4(str); break;
						case 5: take5(str); break;
						case 9: take9(str); break;
					}
				}
			}
			
		} catch(IOException e){
			Global.error("Take internet message");
		}
	}
	
	public void stopThread(){
		takeMessage = false;
	}
	
	public void startThread(){
		takeMessage = true;
	}
	
	public void take0(String str){//Данные танка врага
		boolean enemyExist;
		int i, emptySlot = -1;
		String name;
		name = Global.linkCS.parsString(str,6);
		enemyExist = false;
		for (i=0;i<Global.enemy.length;i++){
			if (Global.enemy[i] != null){
				if (name.equals(Global.enemy[i].name)){
					enemyExist = true;
					break;
				}
			} else {
				emptySlot = i;
			}
		}
		if (enemyExist){
			Global.enemy[i].setData(str);
		} else {
			double x = Double.parseDouble(Global.linkCS.parsString(str,2));
			double y = Double.parseDouble(Global.linkCS.parsString(str,3));
			double direction = Double.parseDouble(Global.linkCS.parsString(str,4));
			Global.enemy[emptySlot] = new Enemy(x,y,direction,this.game,name);
		}
	}
	
	public void take1(String str){//Противник выстрелил
		double x = Double.parseDouble(Global.linkCS.parsString(str,2));
		double y = Double.parseDouble(Global.linkCS.parsString(str,3));
		double direction = Double.parseDouble(Global.linkCS.parsString(str,4));
		double speed = Double.parseDouble(Global.linkCS.parsString(str,5));
		String bullName = Global.linkCS.parsString(str,6);
		String name = Global.linkCS.parsString(str,7);
		long idNet = Integer.parseInt(Global.linkCS.parsString(str,8));
		switch (bullName){
			case "main.player.bullet.DefaultBullet": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,Global.b_default,name,idNet,this.game));
		}
	}
	
	public void take2(String str){//Пуля противника уничтожена
		long idNet = Integer.parseInt(Global.linkCS.parsString(str,2));
		String name = Global.linkCS.parsString(str,3);
		EnemyBullet eb;
		for (int i=0; i<Global.enemyBullet.size();i++){
			eb = (EnemyBullet) Global.enemyBullet.get(i);
			if ((eb.enemyName.equals(name)) && (eb.idNet == idNet)){
				eb.destroy();
			}
		}
	}
	
	public void take3(String str){//Кому-то нанесен урон
		String name = Global.linkCS.parsString(str,2);
		if (name.equals(game.name)){
			Global.player.getArmor().setHp(Global.player.getArmor().getHp()-Double.parseDouble(Global.linkCS.parsString(str,3)));
		}
	}
	
	public void take4(String str){//Танк противника уничтожен
		String name = Global.linkCS.parsString(str,2);
		for (int i = 0;i<Global.enemy.length;i++){
			if (name.equals(Global.enemy[i].name)){
				Global.enemy[i].destroyExtended();
			}
		}
		
		boolean allDestroy = true;
		for (int i = 0;i<Global.enemy.length;i++){
			if (!Global.enemy[i].getDestroy()){
				allDestroy = false;
			}
		}
		
		if (allDestroy){
			Global.clientSend.send5();
			stopThread();
			game.startRestart();
		}
		
	}
	
	public void take5(String str){//Перезагрузка карты
		stopThread();
		game.startRestart();
	}
	
	public void take9(String str){
		Global.pingCheck.takePing();
	}
}
