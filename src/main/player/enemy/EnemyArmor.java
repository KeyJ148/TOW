package main.player.enemy;

import main.*;
import main.image.Animation;

public class EnemyArmor extends Obj{
	
	public Enemy enemy;
	
	public EnemyArmor(Animation anim, Enemy enemy, Game game){
		super(enemy.getX(),enemy.getY(),0.0,enemy.getDirection(),0,true,anim,game);
		this.enemy = enemy;
	}
	
	public Enemy getEnemy(){
		return this.enemy;
	}
	
}