package tow.player.gun;

import tow.image.TextureManager;
import tow.player.Gun;
import tow.player.Player;

public class GDefault extends Gun {
	
	public GDefault(Player player){
		super(player, TextureManager.g_default);
	}
}