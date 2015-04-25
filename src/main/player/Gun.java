package main.player;

import main.player.bullet.*;
import main.*;

public class Gun extends Obj{
	
	private Player player;
	private Game game;
	private int attackSpeed1;//скорость атаки
	private int attackSpeed2;
	private double directionTrunkUp;//скорость поворота дула
	
	private String bullet1;//тип патрона
	private String bullet2;
	
	private double damage1;//дамаг
	private double damage2;
	
	private int trunk1X;//коры смещения от центра
	private int trunk1Y;
	private int trunk2X;
	private int trunk2Y;
	
	private int TPSFromAttack1 = 10000;//технические(стартовые)
	private int TPSFromAttack2 = 10000;
	
	private boolean mousePress1 = false; //технические(зажатие мыши)
	private boolean mousePress2 = false;
	
	public Gun(Player player, Game game, Sprite sprite){
		super(player.getX(),player.getY(),0.0,player.getDirection(),-1,false,sprite,game);
		this.player = player;
		this.game = game;
	}
	
	public void attack1(){
		if (( TPSFromAttack1 > attackSpeed1) && (attackSpeed1 != -1) && (player.getControlAtack())){
			switchBullet(bullet1, trunk1X, trunk1Y, damage1);
			TPSFromAttack1 = 0;
		}
	}
	
	public void attack2(){
		if ((TPSFromAttack2 > attackSpeed2) && (attackSpeed2 != -1) && (player.getControlAtack())){
			switchBullet(bullet2, trunk2X, trunk2Y, damage2);
			TPSFromAttack2 = 0;
		}
	}
	
	public void switchBullet(String bullet, int trunkX, int trunkY, double damage){
		double trunkXdx = trunkX*Math.cos(Math.toRadians(player.getGun().getDirection())-Math.PI/2);//первый отступ "вперед"
		double trunkXdy = trunkX*Math.sin(Math.toRadians(player.getGun().getDirection())-Math.PI/2);//в отличие от маски мы отнимаем от каждого по PI/2
		double trunkYdx = trunkY*Math.cos(Math.toRadians(player.getGun().getDirection())-Math.PI);//потому что изначально и теустуры измененное направление
		double trunkYdy = trunkY*Math.sin(Math.toRadians(player.getGun().getDirection())-Math.PI);//второй отступ "вбок"
		switch(bullet){
			case "DefaultBullet": new DefaultBullet(this.player,player.getXcenter()+trunkXdx+trunkYdx,player.getYcenter()-trunkXdy-trunkYdy,player.getGun().getDirection(),damage,this.game); break;
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
		double trunkUp = getDirectionTrunkUp()+player.getArmor().getDirectionTrunkUp();
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
	
	public Game getGame(){
		return game;
	}
	public Player getPlayer(){
		return player;
	}
	
	public int getAttackSpeed1(){
		return attackSpeed1;
	}
	
	public int getAttackSpeed2(){
		return attackSpeed2;
	}
	
	public double getDirectionTrunkUp(){
		return directionTrunkUp;
	}

	public void setAttackSpeed1(int attackSpeed){
		this.attackSpeed1 = attackSpeed;
	}
	
	public void setAttackSpeed2(int attackSpeed){
		this.attackSpeed2 = attackSpeed;
	}
		
	public void setBullet1(String s){
		this.bullet1 = s;
	}
	public void setBullet2(String s){
		this.bullet2 = s;
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
	
	public void setDirectionTrunkUp(double dir){
		directionTrunkUp = dir;
	}
}