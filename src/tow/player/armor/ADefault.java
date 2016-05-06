package tow.player.armor;

import tow.image.TextureManager;
import tow.player.Armor;
import tow.player.Player;

public class ADefault extends Armor {
	
	public ADefault(Player player){
		super(player,TextureManager.a_default);
	}
}
