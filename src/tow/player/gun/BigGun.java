package tow.player.gun;

import tow.Global;
import tow.player.Gun;
import tow.player.Player;

public class BigGun  extends Gun {
	
	public BigGun(Player player){
		super(player, Global.biggun);
	}
}