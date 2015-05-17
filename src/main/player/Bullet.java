package main.player;

import main.image.Sprite;
import main.player.enemy.*;
import main.*;

public class Bullet extends Obj{
	
	@SuppressWarnings("unused")
	private Player player;
	private double damage;//дамаг пушки+патрона
	private long idNet;
	
	public Bullet(Player player, double x, double y, double speed, double dir,double damage, Sprite sprite){
		super(x,y,speed,dir,0,true,sprite);
		this.player = player;
		this.damage = damage;
		
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
	
	public double getDamage(){
		return damage;
	}
	
	public long getIdNet(){
		return idNet;
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
}