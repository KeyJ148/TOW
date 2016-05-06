package tow.player.gun;

import tow.image.TextureManager;
import tow.player.Gun;
import tow.player.Player;

public class GSniper extends Gun {

	public GSniper(Player player) {
		super(player, TextureManager.g_sniper);
	}

}
