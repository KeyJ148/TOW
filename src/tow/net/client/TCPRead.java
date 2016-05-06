package tow.net.client;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import tow.Global;
import tow.image.TextureHandler;
import tow.image.TextureManager;
import tow.player.Box;
import tow.player.enemy.Enemy;
import tow.player.enemy.EnemyBullet;

public class TCPRead extends Thread{
	
	public volatile ArrayList<String> messages = new ArrayList<String>();
	public volatile boolean takeMessage = true;
	
	public void run(){
		//постоянный обмен данными
		//на TCP
		resumeThread();
		
		String str;
		while(true){
			str = Global.tcpControl.read();
			if (!takeMessage){
				Global.game.startRestart();
				while (!takeMessage){
					try { Thread.sleep(0,100); } catch (InterruptedException e) {}
				}
			}
				
			if (takeMessage){
				if (Integer.parseInt(Global.parsString(str,1)) != 9){//Если не пинг, то ждём update и там обрабатываем
					synchronized (messages){
						messages.add(str);
					}
				} else {//Пинг обрабатываем мгновенно
					take9(str);
				}
			}
		}
	}
	
	public void pauseThread(){
		takeMessage = false;
	}
	
	public void resumeThread(){
		takeMessage = true;
	}
	
	public void update(){
		String str;
		synchronized(messages){
			
			for (int i=0;i<messages.size();i++){
				if (takeMessage){
					str = messages.get(i);
					switch (Integer.parseInt(Global.parsString(str,1))){
						case 0: take0(str); break;
						case 1: take1(str); break;
						case 2: take2(str); break;
						case 3: take3(str); break;
						case 4: take4(str); break;
						case 5: take5(str); break;
						case 10: take10(str); break;
						case 11: take11(str); break;
						case 12: take12(str); break;
						case 13: take13(str); break;
						case 14: take14(str); break;
						case 15: take15(str); break;
					}
				}
			}
			messages.clear();
			
		}
	}
	
	public void take0(String str){//Данные танка врага
		boolean enemyExist;
		int i, emptySlot = -1;
		String name;
		name = Global.parsString(str,6);
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
			double x = Double.parseDouble(Global.parsString(str,2));
			double y = Double.parseDouble(Global.parsString(str,3));
			double direction = Double.parseDouble(Global.parsString(str,4));
			Global.enemy[emptySlot] = new Enemy(x,y,direction,name);
			Global.tcpSend.send10(name);
		}
	}
	
	public void take1(String str){//Противник выстрелил
		double x = Double.parseDouble(Global.parsString(str,2));
		double y = Double.parseDouble(Global.parsString(str,3));
		double direction = Double.parseDouble(Global.parsString(str,4));
		double speed = Double.parseDouble(Global.parsString(str,5));
		String bullName = Global.parsString(str,6);
		String name = Global.parsString(str,7);
		long idNet = Integer.parseInt(Global.parsString(str,8));
		switch (bullName){
			case "BDefault": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,TextureManager.b_default,name,idNet)); break;
			case "BSteel": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,TextureManager.b_steel,name,idNet)); break;
			case "BMass": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,TextureManager.b_mass,name,idNet)); break;
			case "BMassSmall": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,TextureManager.b_mass_small,name,idNet)); break;
			case "BSquare": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,TextureManager.b_square,name,idNet)); break;
			case "BFury": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,TextureManager.b_fury,name,idNet)); break;
			case "BStreamlined": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,TextureManager.b_streamlined,name,idNet)); break;
			case "BPatron": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,TextureManager.b_patron,name,idNet)); break;
			case "BVampire": Global.enemyBullet.add(new EnemyBullet(x,y,speed,direction,TextureManager.b_vampire,name,idNet)); break;
		}
	}
	
	public void take2(String str){//Пуля противника уничтожена
		long idNet = Integer.parseInt(Global.parsString(str,2));
		String name = Global.parsString(str,3);
		EnemyBullet eb;
		for (int i=0; i<Global.enemyBullet.size();i++){
			eb = (EnemyBullet) Global.enemyBullet.get(i);
			if ((eb.enemyName.equals(name)) && (eb.idNet == idNet)){
				eb.destroy();
			}
		}
	}
	
	public void take3(String str){//Кому-то нанесен урон
		String name = Global.parsString(str,2);
		if (name.equals(Global.name)){
			Global.player.getArmor().setHp(Global.player.getArmor().getHp()-Double.parseDouble(Global.parsString(str,3)));
		}
	}
	
	public void take4(String str){//Танк противника уничтожен
		String name = Global.parsString(str,2);
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
			Global.tcpSend.send5();
			pauseThread();
		}
		
	}
	
	public void take5(String str){//Перезагрузка карты
		pauseThread();
	}
	
	public void take9(String str){//Сервер вернул пинг
		Global.pingCheck.takePing();
	}
	
	public void take10(String str){//Враг запрашивает чьи-то данные
		if (Global.parsString(str,2).equals(Global.name)){
			Global.tcpSend.send11();
		}
	}
	
	public void take11(String str){//Враг послал кому-то свои данные
		String enemyName = Global.parsString(str,2);
		for (int i=0;i<Global.enemy.length;i++){
			if ((Global.enemy[i] != null) && (enemyName.equals(Global.enemy[i].name))){
				if (!Global.enemy[i].haveData){
					Global.enemy[i].haveData = true;
					int red = Integer.parseInt(Global.parsString(str,3));
					int green = Integer.parseInt(Global.parsString(str,4));
					int blue = Integer.parseInt(Global.parsString(str,5));
					Global.enemy[i].c = new Color(red, green, blue);
					Global.enemy[i].setColor();
				}
				break;
			}
		}
	}
	
	public void take12(String str){//Сервер создал ящик
		int x = Integer.parseInt(Global.parsString(str,2));
		int y = Integer.parseInt(Global.parsString(str,3));
		int idBox = Integer.parseInt(Global.parsString(str,4));
		int typeBox = Integer.parseInt(Global.parsString(str,5));
		TextureHandler s;
		switch (typeBox){
			case 0: s = TextureManager.box_armor; break;
			case 1: s = TextureManager.box_gun; break;
			case 2: s = TextureManager.box_bullet; break;
			case 3: s = TextureManager.box_health; break;
			case 4: s = TextureManager.box_healthfull; break;
			default: s = TextureManager.error; Global.error("Not find type box"); break;
		}
		new Box(x,y,idBox,s).typeBox = typeBox;
	}
	
	public void take13(String str){//Враг подобрал ящик
		int idBoxDestroy = Integer.parseInt(Global.parsString(str,2));
		for (int i = 0; i<Global.getSize(); i++){
			if ((Global.getObj(i) instanceof Box)){
				Box box = (Box) Global.getObj(i);
				if (box.idBox == idBoxDestroy) box.destroy();
			}
		}
	}
	
	public void take14(String str){//Кто-то подобрал броню
		String enemyName = Global.parsString(str,2);
		for (int i=0;i<Global.enemy.length;i++){
			if (enemyName.equals(Global.enemy[i].name)){
				Global.enemy[i].newArmor(Global.parsString(str,3));
				break;
			}
		}
	}
	
	public void take15(String str){//Кто-то подобрал пушку
		String enemyName = Global.parsString(str,2);
		for (int i=0;i<Global.enemy.length;i++){
			if (enemyName.equals(Global.enemy[i].name)){
				Global.enemy[i].newGun(Global.parsString(str,3));
				break;
			}
		}
	}
}
