package main.player.bullet;

import main.player.*;
import main.*;

public class DefaultBullet extends Bullet{
	
	public DefaultBullet(Player player, double x,double y, double dir,double damage, Game game){
		super(player,x, y, 1.5, dir,2.0+damage, Global.b_default, game);
		//super(player,x, y, SPEED, dir,DAMAGE_BULLET+damage, SPRITE, game);
	}
	
	@Override
	public void directionDrawEqulas(){
		setDirectionDraw(0);
	}
}

