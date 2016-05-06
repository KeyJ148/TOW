package tow.player.gun;

import tow.image.TextureManager;
import tow.player.Gun;
import tow.player.Player;

public class GDouble extends Gun {
	
	public GDouble(Player player){
		super(player, TextureManager.g_double);
	}
}