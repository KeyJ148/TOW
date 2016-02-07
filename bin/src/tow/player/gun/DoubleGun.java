package tow.player.gun;

import tow.Global;
import tow.player.Gun;
import tow.player.Player;

public class DoubleGun extends Gun {
	
	public DoubleGun(Player player){
		super(player, Global.doublegun);
	}
}