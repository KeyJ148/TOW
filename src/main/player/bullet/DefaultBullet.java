package main.player.bullet;

import main.player.*;
import main.*;

public class DefaultBullet extends Bullet{
	
	public DefaultBullet(Player player, double x,double y, double dir,double damage){
		super(player,x, y, dir,damage, Global.b_default);
	}
	
	@Override
	public void directionDrawEqulas(){
		setDirectionDraw(0);
	}
}

