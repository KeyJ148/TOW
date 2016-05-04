package tow.player.bullet;

import tow.Global;
import tow.player.Bullet;
import tow.player.Player;

public class BFury extends Bullet {

	public BFury(Player player, double x, double y, double dir, double damage, int range) {
		super(player, x, y, dir, damage, range, Global.b_fury);
	}

}
