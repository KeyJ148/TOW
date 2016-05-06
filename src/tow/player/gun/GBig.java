package tow.player.gun;

import tow.image.TextureManager;
import tow.player.Gun;
import tow.player.Player;

public class GBig  extends Gun {
	
	public GBig(Player player){
		super(player, TextureManager.g_big);
	}
}