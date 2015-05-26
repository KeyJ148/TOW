package main.player.bullet;

import main.Global;
import main.player.Bullet;
import main.player.Player;

public class StellBullet extends Bullet{
	
	public StellBullet(Player player, double x,double y, double dir,double damage){
		super(player,x, y, dir,damage, Global.b_default);
	}
	
	@Override
	public void directionDrawEqulas(){
		setDirectionDraw(0);
	}
}
