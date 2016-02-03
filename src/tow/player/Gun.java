package tow.player;

import tow.image.Sprite;
import tow.obj.Obj;
import tow.player.bullet.DefaultBullet;
import tow.player.bullet.MassBullet;
import tow.player.bullet.SteelBullet;
import tow.setting.ConfigReader;

public class Gun extends Obj{
	
	private Player player;
	private double attackSpeed1;//скорость атаки
	private double attackSpeed2;//Кол-во выстрелов в секунду
	private double directionGunUp;//скорость поворота дула
	private int range;//Дальность выстрела
	
	private double damage1;//дамаг
	private double damage2;
	
	private int trunk1X;//коры смещения от центра
	private int trunk1Y;
	private int trunk2X;
	private int trunk2Y;
	
	public String[] allowArmor;
	public String[] allowBullet;
	
	private final String pathSetting = "gun/";
	
	private long nanoSecFromAttack1 = 0;//технические(стартовые)
	private long nanoSecFromAttack2 = 0;//Кол-во времени до конца перезарядки в наносекундах
	
	private boolean mousePress1 = false; //технические(зажатие мыши)
	private boolean mousePress2 = false;
	
	public Gun(Player player, Sprite sprite){
		super(player.getX()-sprite.getWidth()/2,player.getY()-sprite.getHeight()/2,0.0,player.getDirection(),-1,false,sprite);
		this.player = player;
		loadData(getClass().getName());
	}
	
	public void attack1(){
		if ((nanoSecFromAttack1 <= 0) && (attackSpeed1 > 0) && (player.getControlAtack())){
			switchBullet(player.getBullet(), trunk1X, trunk1Y, damage1);
			nanoSecFromAttack1 = (long) ((double) 1/attackSpeed1*1000*1000*1000);
		}
	}
	
	public void attack2(){
		if ((nanoSecFromAttack2 <= 0) && (attackSpeed2 > 0) && (player.getControlAtack())){
			switchBullet(player.getBullet(), trunk2X, trunk2Y, damage2);
			nanoSecFromAttack2 = (long) ((double) 1/attackSpeed2*1000*1000*1000);
		}
	}
	
	public void switchBullet(String bullet, int trunkX, int trunkY, double damage){
		double trunkXdx = trunkX*Math.cos(Math.toRadians(player.getGun().getDirection())-Math.PI/2);//первый отступ "вперед"
		double trunkXdy = trunkX*Math.sin(Math.toRadians(player.getGun().getDirection())-Math.PI/2);//в отличие от маски мы отнимаем от каждого по PI/2
		double trunkYdx = trunkY*Math.cos(Math.toRadians(player.getGun().getDirection())-Math.PI);//потому что изначально у теустуры измененное направление
		double trunkYdy = trunkY*Math.sin(Math.toRadians(player.getGun().getDirection())-Math.PI);//второй отступ "вбок"
		switch(bullet){
			case "DefaultBullet": new DefaultBullet(this.player,player.getXcenter()+trunkXdx+trunkYdx,player.getYcenter()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "SteelBullet": new SteelBullet(this.player,player.getXcenter()+trunkXdx+trunkYdx,player.getYcenter()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "MassBullet": new MassBullet(this.player,player.getXcenter()+trunkXdx+trunkYdx,player.getYcenter()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
		}
	}
	
	@Override
	public void updateChildMid(long delta){
		//поворот дула (много костылей)
		double pointDir = -Math.toDegrees(Math.atan((getYViewCenter()-player.getMouseY())/(getXViewCenter()-player.getMouseX())));
		double trunkUp = ((double) delta/1000000000)*(getDirectionGunUp()+player.getArmor().getDirectionGunUp());
		if ((getXViewCenter()-player.getMouseX())>0){
			pointDir+=180;
		} else if ((getYViewCenter()-player.getMouseY())<0){
			pointDir+=360;
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
		
		if ((Math.abs(pointDir - getDirection()) < trunkUp*1.5) || (Math.abs(pointDir - getDirection()) > 360-trunkUp*1.5)){
			setDirection(pointDir);
		}
		
		//Выстрел
		nanoSecFromAttack1 -= delta;
		nanoSecFromAttack2 -= delta;
		if (mousePress1){
			attack1();
		}
		if (mousePress2){
			attack2();
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
		
		attackSpeed1 = cr.findDouble("ATTACK_SPEED_1");
		attackSpeed2 = cr.findDouble("ATTACK_SPEED_2");
		trunk1X = cr.findInteger("TRUNK_1_X");
		trunk1Y = cr.findInteger("TRUNK_1_Y");
		trunk2X = cr.findInteger("TRUNK_2_X");
		trunk2Y = cr.findInteger("TRUNK_2_Y");
		
		directionGunUp = cr.findDouble("DIRECTION_UP");
		damage1  = cr.findDouble("DAMAGE_1");
		damage2  = cr.findDouble("DAMAGE_2");
		range = cr.findInteger("RANGE");
		
		allowArmor = player.parseAllow(cr.findString("ALLOW_ARMOR"));
		allowBullet = player.parseAllow(cr.findString("ALLOW_BULLET"));
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