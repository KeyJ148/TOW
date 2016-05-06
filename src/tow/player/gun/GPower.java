package tow.player.gun;

import tow.image.TextureManager;
import tow.player.Gun;
import tow.player.Player;

public class GPower extends Gun {
	
	public GPower(Player player){
		super(player, TextureManager.g_power);
	}
}