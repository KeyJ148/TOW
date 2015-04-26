package main.net;

import java.io.*;
import java.net.*;

import main.*;
import main.home.*;
import main.player.*;
import main.player.enemy.*;
import main.net.ClientNetSend;

public class ClientNetThread extends Thread{
	
	public DataInputStream in;
	public DataOutputStream out;
	public Socket sock;
	public Game game;
	
	public ClientNetThread(DataInputStream in, DataOutputStream out, Socket sock){
		this.in = in;
		this.out = out;
		this.sock = sock;
	}
	
	public void initMap(Game game){
		if (Game.console) System.out.println("Download map start.");
		this.game = game;
		
		//Скачивание карты
		String s = "";
		//Разрешение карты(для камеры)
		try{
			s = this.in.readUTF();
			if (Game.console) System.out.println("Download map size complite.");
		} catch(IOException e){
			System.out.println("[ERROR] Download map size");
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
		if (Game.console) System.out.println("Download map object start.");
		while(true){
			try{
				s = this.in.readUTF();
				
				if (s.equals("-1 ")){
					if (Game.console) System.out.println("Download map object complite.");
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
				
			} catch(IOException e){
				System.out.println("[ERROR] Download map");
				System.exit(0);
			}
		}
		genTank();
	}
	
	//получение данных
	public void genTank(){
		try{
			this.out.writeUTF(game.name);//отправка имени
			String s = this.in.readUTF();//получение кор танка
			double x = (double) Integer.parseInt(s.substring(0,s.indexOf(' ')));
			double y = (double) Integer.parseInt(s.substring(s.indexOf(' ')+1));
			s = this.in.readUTF();//получение кол-во игрков
			this.game.peopleMax = Integer.parseInt(s.substring(0,s.indexOf(' ')));
			Global.enemy = new Enemy[this.game.peopleMax-1];
			Global.player = new Player(x,y,Math.random()*360,game);
			
			if (Game.console) System.out.println("Generation tank complite.");
		} catch(IOException e){
			System.out.println("[ERROR] Message about generation tank");
			System.exit(0);
		}
		new ClientNetSend(this.out, this.game);
		start();
	}
	
	public void run(){
		//постоянный обмен данными
		//на TCP
		String str;
		while(true){
			try{
				str = this.in.readUTF();
				switch (Integer.parseInt(Global.linkCS.parsString(str,1))){
					case 0: take0(str); break;
					case 1: take1(str); break;
					case 2: take2(str); break;
					case 3: take3(str); break;
					case 4: take4(str); break;
					case 5: take5(str); break;
				}
			} catch(IOException e){
				System.out.println("[ERROR] Take internet message");
			}
		}
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
			game.startRestart();
		}
		
	}
	
	public void take5(String str){//Перезагрузка карты
		game.startRestart();
	}
}
