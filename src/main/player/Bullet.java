package main.player;

import main.player.enemy.*;
import main.*;

public class Bullet extends Obj{
	
	@SuppressWarnings("unused")
	private Player player;
	private Game game;
	private double damage;//дамаг пушки+патрона
	private long idNet;
	
	public Bullet(Player player, double x, double y, double speed, double dir,double damage, Sprite sprite, Game game){
		super(x,y,speed,dir,0,true,sprite,game);
		this.game = game;
		this.player = player;
		this.damage = damage;
		
		this.idNet = Global.idNet;
		Global.clientSend.sendData("1 " + getX() + " " + getY() + " " + getDirection() + " " + getSpeed() + " " + getClass().getName() + " " + game.name + " " + Global.idNet + " ");
		Global.idNet++;
		
		setCollObj(new String[] {"main.home.Home", "main.player.enemy.EnemyArmor"});
	}
	
	public void collReport(Obj obj){
		if (obj.getClass().getName().equals("main.home.Home")){
			destroyBullet();
		}
		if (obj.getClass().getName().equals("main.player.enemy.EnemyArmor")){
			EnemyArmor ea = (EnemyArmor) obj;
			Global.clientSend.sendData("3 " + ea.enemy.name + " " + damage + " ");
			destroyBullet();
		}
	}
	
	public void destroyBullet(){
		Global.clientSend.sendData("2 " + this.idNet + " " + this.game.name + " ");
		destroy();
	}
	
	public double getDamage(){//на будущие
		return damage;
	}
	
	public void updateChildStart(){
		if (!getDestroy()){
			try{
				if ((getX()<0) || (getY()<0) || (getX()>game.widthMap) || (getY()>game.heightMap)){
					destroyBullet();
				}
			}catch(NullPointerException e){
				p("Bullet exception!");
			}
		}
	}
}