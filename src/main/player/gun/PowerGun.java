package main.player.gun;

import main.Global;
import main.player.Gun;
import main.player.Player;

public class PowerGun extends Gun {
	
	public PowerGun(Player player){
		super(player, Global.powergun);
	}
}