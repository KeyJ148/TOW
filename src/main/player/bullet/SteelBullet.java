package main.player.bullet;

import main.Global;
import main.player.Bullet;
import main.player.Player;

public class SteelBullet extends Bullet{
	
	public SteelBullet(Player player, double x,double y, double dir,double damage, int range){
		super(player,x, y, dir,damage, range, Global.b_steel);
	}
	
	@Override
	public void directionDrawEqulas(){
		setDirectionDraw(0);
	}
}
