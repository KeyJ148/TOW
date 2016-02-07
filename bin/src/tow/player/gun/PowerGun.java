package tow.player.gun;

import tow.Global;
import tow.player.Gun;
import tow.player.Player;

public class PowerGun extends Gun {
	
	public PowerGun(Player player){
		super(player, Global.powergun);
	}
}