package tow.player;

import tow.Global;
import tow.image.TextureHandler;
import tow.obj.Obj;
import tow.player.bullet.BDefault;
import tow.player.bullet.BFury;
import tow.player.bullet.BMass;
import tow.player.bullet.BMassSmall;
import tow.player.bullet.BPatron;
import tow.player.bullet.BSquare;
import tow.player.bullet.BSteel;
import tow.player.bullet.BStreamlined;
import tow.player.bullet.BVampire;
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
	
	public Gun(Player player, TextureHandler textureHandler){
		super(player.getX()-textureHandler.getWidth()/2,player.getY()-textureHandler.getHeight()/2,0.0,player.getDirection(),-1,false,textureHandler);
		this.player = player;
		loadData(getClass().getName());
	}
	
	public void attack1(){
		if ((nanoSecFromAttack1 <= 0) && (attackSpeed1 > 0)){
			switchBullet(player.getBullet(), trunk1X, trunk1Y, damage1);
			nanoSecFromAttack1 = (long) ((double) 1/attackSpeed1*1000*1000*1000);
		}
	}
	
	public void attack2(){
		if ((nanoSecFromAttack2 <= 0) && (attackSpeed2 > 0)){
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
			case "BDefault": new BDefault(this.player,player.getX()+trunkXdx+trunkYdx,player.getY()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "BSteel": new BSteel(this.player,player.getX()+trunkXdx+trunkYdx,player.getY()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "BMass": new BMass(this.player,player.getX()+trunkXdx+trunkYdx,player.getY()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "BMassSmall": new BMassSmall(this.player,player.getX()+trunkXdx+trunkYdx,player.getY()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "BSquare": new BSquare(this.player,player.getX()+trunkXdx+trunkYdx,player.getY()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "BFury": new BFury(this.player,player.getX()+trunkXdx+trunkYdx,player.getY()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "BStreamlined": new BStreamlined(this.player,player.getX()+trunkXdx+trunkYdx,player.getY()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "BPatron": new BPatron(this.player,player.getX()+trunkXdx+trunkYdx,player.getY()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
			case "BVampire": new BVampire(this.player,player.getX()+trunkXdx+trunkYdx,player.getY()-trunkXdy-trunkYdy,getDirection(),damage, range); break;
		}
	}
	
	@Override
	public void updateChildMid(long delta){
		//поворот дула (много костылей)
		double pointDir = -Math.toDegrees(Math.atan((getYView()-Global.mouseHandler.mouseY)/(getXView()-Global.mouseHandler.mouseX)));
		
		double trunkUp = ((double) delta/1000000000)*(getDirectionGunUp()+player.getArmor().getDirectionGunUp());
		if ((getXView()-Global.mouseHandler.mouseX)>0){
			pointDir+=180;
		} else if ((getYView()-Global.mouseHandler.mouseY)<0){
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
		if (Global.mouseHandler.mouseDown1){
			attack1();
		}
		if (Global.mouseHandler.mouseDown2){
			attack2();
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