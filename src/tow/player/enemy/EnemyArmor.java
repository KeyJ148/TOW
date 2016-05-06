package tow.player.enemy;

import tow.image.TextureHandler;
import tow.obj.Obj;

public class EnemyArmor extends Obj{
	
	public Enemy enemy;
	
	public EnemyArmor(TextureHandler[] textureHandler, Enemy enemy){
		super(enemy.getX(),enemy.getY(),0.0,enemy.getDirection(),0,true,textureHandler);
		this.enemy = enemy;
	}
	
	public Enemy getEnemy(){
		return enemy;
	}
	
}