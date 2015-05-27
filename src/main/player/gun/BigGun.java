package main.player.gun;

import main.Global;
import main.player.Gun;
import main.player.Player;

public class BigGun  extends Gun {
	
	public BigGun(Player player){
		super(player, Global.biggun);
	}
}