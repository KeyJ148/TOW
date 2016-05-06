package tow.player;

import java.awt.Font;
import java.util.Random;
import java.util.Vector;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import tow.Global;
import tow.image.TextureManager;
import tow.obj.Obj;
import tow.player.armor.ADefault;
import tow.player.armor.AElephant;
import tow.player.armor.AFortified;
import tow.player.armor.AFury;
import tow.player.armor.AMite;
import tow.player.armor.AVampire;
import tow.player.gun.GBig;
import tow.player.gun.GDefault;
import tow.player.gun.GDouble;
import tow.player.gun.GFury;
import tow.player.gun.GKkp;
import tow.player.gun.GMortar;
import tow.player.gun.GPower;
import tow.player.gun.GRocketd;
import tow.player.gun.GSniper;
import tow.player.gun.GVampire;
import tow.setting.ConfigReader;
import tow.title.Title;

/*
При добавление необходимо менять...
Броня:
Player.newArmor, Enemy.newArmor, Global, Global.initSprite, tow.player.armor, res/settings/armor, res/settings/gun
Оружие:
Player.newGun, Enemy.newArmor, Global, Global.initSprite, tow.player.gun, res/settings/gun, res/settings/armor, res/settings/bullet
Патроны:
Gun.switchBullet, TCPRead.take1, Global, Global.initSprite, tow.player.bullet, res/settings/gun, res/settings/bullet
*/

public class Player extends Obj{
	
	public boolean takeArmor = true;//Разрешение на подбор определённого ящика
	public boolean takeGun = true;
	public boolean takeBullet = true;
	public boolean takeHealth = true;
	
	private long lastSend = 0;//Время с последней отправки
	
	private Gun gun;
	private String bullet;
	private Armor armor;
	
	public Player(double x, double y, double direction){
		super(x,y,0.0,direction,1,false,TextureManager.player_sys);
		this.bullet = "BDefault";
		this.armor = new ADefault(this);
		this.gun = new GDefault(this);
		setColor();
	}
	
	//Обработка потока ввода
	@Override
	public void updateChildMid(long delta){
		for (int i=0; i<Global.keyboardHandler.bufferState.size(); i++){
			if (Global.keyboardHandler.bufferState.get(i)){//Клавиша нажата
				if (armor.getControlMotion()){
					switch (Global.keyboardHandler.bufferKey.get(i)){
						case Keyboard.KEY_W: 
							armor.setSpeed(armor.getSpeedTankUp());
							armor.setRunUp(true);
							armor.setRunDown(false);
						break;
						case Keyboard.KEY_A: 
							armor.setTurnLeft(true);
							armor.setTurnRight(false);
						break;
						case Keyboard.KEY_D: 
							armor.setTurnRight(true);
							armor.setTurnLeft(false);
						break;
						case Keyboard.KEY_S: 
							armor.setSpeed(armor.getSpeedTankDown());
							armor.setRunDown(true);
							armor.setRunUp(false);
						break;
					}
				}
				
				//Клавиши запрета и разрешения на подбор ящиков
				switch (Global.keyboardHandler.bufferKey.get(i)){
					case Keyboard.KEY_1:
						if (takeArmor){
							takeArmor = false;
						} else {
							takeArmor = true;
						}
					break;
					case Keyboard.KEY_2:
						if (takeGun){
							takeGun = false;
						} else {
							takeGun = true;
						}
					break;
					case Keyboard.KEY_3:
						if (takeBullet){
							takeBullet = false;
						} else {
							takeBullet = true;
						}
					break;
					case Keyboard.KEY_4:
						if (takeHealth){
							takeHealth = false;
						} else {
							takeHealth = true;
						}
					break;
				}
			} else {//Клавиша отпущена
				if (armor.getControlMotion()){
					switch (Global.keyboardHandler.bufferKey.get(i)){
						case Keyboard.KEY_W: 
							if (armor.getSpeed() > 0) armor.setSpeed(0.0);//Условие для ситации: W press; S press; W release;
							armor.setRunUp(false);
						break;
						case Keyboard.KEY_A: 
							armor.setTurnLeft(false);
						break;
						case Keyboard.KEY_D: 
							armor.setTurnRight(false);
						break;
						case Keyboard.KEY_S: 
							if (armor.getSpeed() < 0) armor.setSpeed(0.0);
							armor.setRunDown(false);
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void updateChildFinal(long delta){
		//Отрисовка ника и хп
		int nameX = (int) Math.round(getXView()-Global.name.length()*3.25); // lengthChar/2
		int nameY = (int) getYView()-50;
		Global.game.render.addTitle(new Title(nameX, nameY, Global.name));
		
		Global.game.render.addTitle(new Title(1, -3, "HP: " +  Math.round(getArmor().getHp()) + "/" + Math.round(getArmor().getHpMax()), Color.black, 20, Font.BOLD));
		
		//Отправка данных о игроке
		lastSend += delta;
		if (lastSend > Global.setting.SEND_MILLIS*1000000){
			lastSend = 0;
			Global.tcpSend.send0(getData());
		}
	}
	
	public String getData(){
		String s;
		int anim;
		if (armor.getAnimOn()){
			anim = armor.getAnimSpeed();
		} else {
			anim = -1;
		}
		s = Math.round(getX())
			+ " " + Math.round(getY())
			+ " " + Math.round(armor.getDirection())
			+ " " + anim
			+ " " + Global.name
			+ " " + Math.round(gun.getDirection())
			+ " " + Math.round(armor.getSpeed());
		return s;
	}
	
	public void setColor(){
		setColorArmor();
		setColorGun();
	}
	
	public void setColorArmor(){
		armor.getImage().setColor(Global.color);
	}
	
	public void setColorGun(){
		gun.getImage().setColor(Global.color);
	}
	
	public void newEquipment(int typeBox){
		switch (typeBox){
			case 0: if (takeArmor) newArmor(); break;
			case 1: if (takeGun) newGun(); break;
			case 2: if (takeBullet) newBullet(); break;
			case 3: if (takeHealth) getArmor().setHp(getArmor().getHp() + getArmor().getHpMax()*0.4); break;
		}
	}
	
	public void newArmor(){
		boolean runUp = armor.runUp;
		boolean runDown = armor.runDown;
		boolean turnRight = armor.turnRight;
		boolean turnLeft = armor.turnLeft;
		
		String armorNameNow = getArmor().getClass().getName();
		armorNameNow = armorNameNow.substring(armorNameNow.lastIndexOf(".")+1);
		
		Random rand = new Random();
		Armor newArmor;
		String newArmorName;
		while (true){
			newArmorName = getGun().allowArmor[rand.nextInt(getGun().allowArmor.length)];
			if (!newArmorName.equals(armorNameNow)) break;
		}
		switch(newArmorName){
			case "ADefault": newArmor = new ADefault(this); break;
			case "AFortified": newArmor = new AFortified(this); break;
			case "AElephant": newArmor = new AElephant(this); break;
			case "AFury": newArmor = new AFury(this); break;
			case "AMite": newArmor = new AMite(this); break;
			case "AVampire": newArmor = new AVampire(this); break;
			default: newArmor = new ADefault(this); Global.error("Not find new armor name"); break;
		}
		
		double lastArmorDirection = getArmor().getDirection();
		double lastArmorHpPercent = getArmor().getHp()/getArmor().getHpMax();
		getArmor().destroy();
		setArmor(newArmor);
		getArmor().setDirection(lastArmorDirection);
		getArmor().setHp(lastArmorHpPercent * getArmor().getHpMax());
		getArmor().setRunUp(runUp);
		getArmor().setRunDown(runDown);
		getArmor().setTurnRight(turnRight);
		getArmor().setTurnLeft(turnLeft);
		if (runUp) getArmor().setSpeed(getArmor().getSpeedTankUp());
		if (runDown) getArmor().setSpeed(getArmor().getSpeedTankDown());
		setColorArmor();
		String armorName = getArmor().getClass().getName();
		Global.tcpSend.send14(armorName.substring(armorName.lastIndexOf(".")+1));
	}
	
	public void newGun(){
		String gunNameNow = getGun().getClass().getName();
		gunNameNow = gunNameNow.substring(gunNameNow.lastIndexOf(".")+1);
		
		Random rand = new Random();
		String newGunName;
		String[] allowGunForBullet;
		boolean end = false;
		do{
			while (true){
				newGunName = getArmor().allowGun[rand.nextInt(getArmor().allowGun.length)];
				if (!newGunName.equals(gunNameNow)) break;
			}
			ConfigReader cr = new ConfigReader(Bullet.pathSetting + getBullet() + ".properties");
			allowGunForBullet = parseAllow(cr.findString("ALLOW_GUN"));
			for (int i=0;i<allowGunForBullet.length;i++){
				if (newGunName.equals(allowGunForBullet[i])){
					end = true;
				}
			}
		} while(!end);
		
		Gun newGun;
		switch(newGunName){
			case "GDefault": newGun = new GDefault(this); break;
			case "GDouble": newGun = new GDouble(this); break;
			case "GBig": newGun = new GBig(this); break;
			case "GPower": newGun = new GPower(this); break;
			case "GFury": newGun = new GFury(this); break;
			case "GMortar": newGun = new GMortar(this); break;
			case "GRocketd": newGun = new GRocketd(this); break;
			case "GKkp": newGun = new GKkp(this); break;
			case "GSniper": newGun = new GSniper(this); break;
			case "GVampire": newGun = new GVampire(this); break;
			default: newGun = new GDefault(this); Global.error("Not find new gun name"); break;
		}
		
		
		double lastGunDirection = getGun().getDirection();
		getGun().destroy();
		setGun(newGun);
		getGun().setDirection(lastGunDirection);
		setColorGun();
		
		String gunName = getGun().getClass().getName();
		Global.tcpSend.send15(gunName.substring(gunName.lastIndexOf(".")+1));
	}
	
	public void newBullet(){
		Random rand = new Random();
		String newBullet;
		while (true){
			newBullet = getGun().allowBullet[rand.nextInt(getGun().allowBullet.length)];
			if (!newBullet.equals(bullet)) break;
		}
		bullet = newBullet;
	}
	
	public String[] parseAllow(String allowString){
		Vector<String> allowVector = new Vector<String>();
		
		boolean end = false;
		int i = 0;
		String name;
		do{
			i++;
			name = Global.parsString(allowString,i);
			allowVector.add(name);
			if (name.equals(allowString.substring(allowString.lastIndexOf(" ")+1))){
				end = true;
			}
		} while(!end);
		
		return allowVector.toArray(new String[allowVector.size()]);
	}
	
	public Gun getGun(){
		return gun;
	}
	
	public String getBullet(){
		return bullet;
	}
	
	public Armor getArmor(){
		return armor;
	}
	
	public void setGun(Gun gun) {
		this.gun = gun;
	}

	public void setBullet(String bullet) {
		this.bullet = bullet;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
	}
}