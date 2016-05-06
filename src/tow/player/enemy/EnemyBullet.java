package tow.player.enemy;

import tow.Global;
import tow.image.TextureHandler;
import tow.obj.Obj;

public class EnemyBullet extends Obj{
	
	public TextureHandler textureHandler;
	public String enemyName;
	public long idNet;
	
	public EnemyBullet(double x, double y, double speed, double direction, TextureHandler textureHandler, String enemyName, long idNet){
		super(x,y,speed,direction,0,false,textureHandler);
		this.textureHandler = textureHandler;
		this.enemyName = enemyName;
		this.idNet = idNet;
	}
	
	@Override
	public void directionDrawEqulas(){
		if (textureHandler.equals(Global.b_default)){
			setDirectionDraw(0);
		} else {
			super.directionDrawEqulas();
		}
	}
	
}
