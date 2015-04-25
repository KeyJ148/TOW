package main.player.enemy;

import main.*;

public class EnemyBullet extends Obj{
	
	public String enemyName;
	public long idNet;
	
	public EnemyBullet(double x, double y, double speed, double direction,Sprite sprite, String enemyName, long idNet,Game game){
		super(x,y,speed,direction,0,true,sprite,game);
		this.enemyName = enemyName;
		this.idNet = idNet;
	}
	
}
