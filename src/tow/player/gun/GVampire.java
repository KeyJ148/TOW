package tow.player.gun;

import tow.image.TextureManager;
import tow.player.Gun;
import tow.player.Player;

public class GVampire extends Gun {

	public GVampire(Player player) {
		super(player, TextureManager.g_vampire);
	}

}
