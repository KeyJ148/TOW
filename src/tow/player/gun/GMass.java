package tow.player.gun;

import tow.image.TextureManager;
import tow.player.Gun;
import tow.player.Player;

public class GMass  extends Gun {
	
	public GMass(Player player){
		super(player, TextureManager.g_mass);
	}
}