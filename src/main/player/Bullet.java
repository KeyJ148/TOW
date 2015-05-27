package main.player;

import main.image.Sprite;
import main.obj.Obj;
import main.player.enemy.*;
import main.setting.ConfigReader;
import main.*;

public class Bullet extends Obj{
	
	@SuppressWarnings("unused")
	private Player player;
	private double damage;//дамаг пушки+патрона
	private long idNet;
	
	public static final String pathSetting = "bullet/";
	
	public Bullet(Player player, double x, double y, double dir,double damage, Sprite sprite){
		super(x,y,0,dir,0,true,sprite);
		this.player = player;
		this.damage = damage;//Дамаг исключительно от выстрелевшей пушки
		loadData(getClass().getName());
		
		this.idNet = Global.idNet;
		Global.clientSend.send1(this);
		Global.idNet++;
		
		setCollObj(new String[] {"main.home.Home", "main.player.enemy.EnemyArmor"});
	}
	
	public void collReport(Obj obj){
		if (obj.getClass().getName().equals("main.home.Home")){
			destroyBullet();
		}
		if (obj.getClass().getName().equals("main.player.enemy.EnemyArmor")){
			EnemyArmor ea = (EnemyArmor) obj;
			Global.clientSend.send3(this,ea.enemy.name);
			destroyBullet();
		}
	}
	
	public void destroyBullet(){
		Global.clientSend.send2(this);
		destroy();
	}
	
	public void updateChildStart(){
		if (!getDestroy()){
			try{
				if ((getX()<0) || (getY()<0) || (getX()>Global.widthMap) || (getY()>Global.heightMap)){
					destroyBullet();
				}
			}catch(NullPointerException e){
				p("[ERROR] Bullet null");
			}
		}
	}
	
	public void loadData(String fileName){
		ConfigReader cr = new ConfigReader(pathSetting + fileName.substring(fileName.lastIndexOf('.')+1) + ".properties");
		
		setSpeed(cr.findDouble("SPEED"));
		damage += cr.findDouble("DAMAGE");//К дамагу пушки прибавляем дамаг патрона
	}
	
	public double getDamage(){
		return damage;
	}
	
	public long getIdNet(){
		return idNet;
	}
}