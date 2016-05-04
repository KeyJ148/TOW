package tow.player.enemy;

import tow.*;
import tow.image.Sprite;
import tow.obj.Obj;

public class EnemyBullet extends Obj{
	
	public Sprite sprite;
	public String enemyName;
	public long idNet;
	
	public EnemyBullet(double x, double y, double speed, double direction,Sprite sprite, String enemyName, long idNet){
		super(x,y,speed,direction,0,false,sprite);
		this.sprite = sprite;
		this.enemyName = enemyName;
		this.idNet = idNet;
	}
	
	@Override
	public void directionDrawEqulas(){
		if (sprite == Global.b_default){
			setDirectionDraw(0);
		} else {
			super.directionDrawEqulas();
		}
	}
	
}
