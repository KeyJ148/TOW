package tow.player.armor;

import tow.image.TextureManager;
import tow.player.Armor;
import tow.player.Player;

public class AFortified extends Armor {
	
	public AFortified(Player player){
		super(player,TextureManager.a_fortified);
	}
}
