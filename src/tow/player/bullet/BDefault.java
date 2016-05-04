package tow.player.bullet;

import tow.*;
import tow.player.*;

public class BDefault extends Bullet{
	
	public BDefault(Player player, double x,double y, double dir,double damage, int range){
		super(player,x, y, dir,damage, range, Global.b_default);
	}
}

