package main.player;

import main.image.Sprite;
import main.obj.Obj;
import main.player.bullet.*;
import main.setting.ConfigReader;
import main.*;

public class Gun extends Obj{
	
	private Player player;
	private double attackSpeed1;//скорость атаки
	private double attackSpeed2;
	private double directionGunUp;//скорость поворота дула
	
	private double damage1;//дамаг
	private double damage2;
	
	private int trunk1X;//коры смещения от центра
	private int trunk1Y;
	private int trunk2X;
	private int trunk2Y;
	
	private final String pathSetting = "gun/";
	
	private int TPSFromAttack1 = 10000;//технические(стартовые)
	private int TPSFromAttack2 = 10000;
	
	private boolean mousePress1 = false; //технические(зажатие мыши)
	private boolean mousePress2 = false;
	
	public Gun(Player player, Sprite sprite){
		super(player.getX(),player.getY(),0.0,player.getDirection(),-1,false,sprite);
		this.player = player;
		loadData(getClass().getName());
	}
	
	public void attack1(){
		if (( TPSFromAttack1 > attackSpeed1) && (attackSpeed1> 0) && (player.getControlAtack())){
			switchBullet(player.getBullet(), trunk1X, trunk1Y, damage1);
			TPSFromAttack1 = 0;
		}
	}
	
	public void attack2(){
		if ((TPSFromAttack2 > attackSpeed2) && (attackSpeed2 > 0) && (player.getControlAtack())){
			switchBullet(player.getBullet(), trunk2X, trunk2Y, damage2);
			TPSFromAttack2 = 0;
		}
	}
	
	public void switchBullet(String bullet, int trunkX, int trunkY, double damage){
		double trunkXdx = trunkX*Math.cos(Math.toRadians(player.getGun().getDirection())-Math.PI/2);//первый отступ "вперед"
		double trunkXdy = trunkX*Math.sin(Math.toRadians(player.getGun().getDirection())-Math.PI/2);//в отличие от маски мы отнимаем от каждого по PI/2
		double trunkYdx = trunkY*Math.cos(Math.toRadians(player.getGun().getDirection())-Math.PI);//потому что изначально и теустуры измененное направление
		double trunkYdy = trunkY*Math.sin(Math.toRadians(player.getGun().getDirection())-Math.PI);//второй отступ "вбок"
		switch(bullet){
			case "DefaultBullet": new DefaultBullet(this.player,player.getXcenter()+trunkXdx+trunkYdx,player.getYcenter()-trunkXdy-trunkYdy,player.getGun().getDirection(),damage); break;
			case "StellBullet": new StellBullet(this.player,player.getXcenter()+trunkXdx+trunkYdx,player.getYcenter()-trunkXdy-trunkYdy,player.getGun().getDirection(),damage); break;
		}
	}
	
	public void updateChildStart(){
		TPSFromAttack1++;
		TPSFromAttack2++;
		if (mousePress1){
			attack1();
		}
		if (mousePress2){
			attack2();
		}
	}
	
	public void updateChildMid(){
		//поворот дула
		double pointDir = -Math.toDegrees(Math.atan((getYViewCenter()-player.getMouseY())/(getXViewCenter()-player.getMouseX())));
		double trunkUp = getDirectionGunUp()+player.getArmor().getDirectionGunUp();
		if ((getXViewCenter()-player.getMouseX())>0){
			pointDir+=180;
		} else if ((getYViewCenter()-player.getMouseY())<0){
			pointDir+=360;
		}
		
		if (Math.abs(pointDir - getDirection()) < trunkUp*2){
			setDirection(pointDir) ;
		}
		if ((pointDir - getDirection()) > 0){
			if ((pointDir - getDirection()) > 180){
				setDirection(getDirection() - trunkUp);
			} else {
				setDirection(getDirection() + trunkUp);
			}
		} else {
			if ((pointDir - getDirection()) < -180){
				setDirection(getDirection() + trunkUp);
			} else {
				setDirection(getDirection() - trunkUp);
			}
		}
	}
	
	public void mousePress(byte gun){
		switch(gun){
			case 1: mousePress1 = true; break;
			case 2: mousePress2 = true; break;
		}
	}
	
	public void mouseReleas(byte gun){
		switch(gun){
			case 1: mousePress1 = false; break;
			case 2: mousePress2 = false; break;
		}
	}
	
	public void loadData(String fileName){
		ConfigReader cr = new ConfigReader(pathSetting + fileName.substring(fileName.lastIndexOf('.')+1) + ".properties");
		
		attackSpeed1 = Global.setting.TPS*cr.findDouble("ATTACK_SPEED_1");
		attackSpeed2 = Global.setting.TPS*cr.findDouble("ATTACK_SPEED_2");
		trunk1X = cr.findInteger("TRUNK_1_X");
		trunk1Y = cr.findInteger("TRUNK_1_Y");
		trunk2X = cr.findInteger("TRUNK_2_X");
		trunk2Y = cr.findInteger("TRUNK_2_Y");
		
		directionGunUp = cr.findDouble("DIRECTION_UP");
		damage1  = cr.findDouble("DAMAGE_1");
		damage2  = cr.findDouble("DAMAGE_2");
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public double getAttackSpeed1(){
		return attackSpeed1;
	}
	
	public double getAttackSpeed2(){
		return attackSpeed2;
	}
	
	public double getDirectionGunUp(){
		return directionGunUp;
	}

	public void setAttackSpeed1(int attackSpeed){
		this.attackSpeed1 = attackSpeed;
	}
	
	public void setAttackSpeed2(int attackSpeed){
		this.attackSpeed2 = attackSpeed;
	}
	
	public void setTrunk1X(int x){
		this.trunk1X = x;
	}
	
	public void setTrunk1Y(int y){
		this.trunk1Y = y;
	}
	
	public void setTrunk2X(int x){
		this.trunk2X = x;
	}
	
	public void setTrunk2Y(int y){
		this.trunk2Y = y;
	}
	
	public void setDamage1(double dmg){
		this.damage1 = dmg;
	}
	
	public void setDamage2(double dmg){
		this.damage2 = dmg;
	}
	
	public void setDirectionGunUp(double dir){
		directionGunUp = dir;
	}
}