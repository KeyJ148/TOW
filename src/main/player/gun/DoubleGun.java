package main.player.gun;

import main.Global;
import main.player.Gun;
import main.player.Player;

public class DoubleGun extends Gun {
	
	public DoubleGun(Player player){
		super(player, Global.doublegun);
	}
}