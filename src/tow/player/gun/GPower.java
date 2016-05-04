package tow.player.gun;

import tow.Global;
import tow.player.Gun;
import tow.player.Player;

public class GPower extends Gun {
	
	public GPower(Player player){
		super(player, Global.g_power);
	}
}