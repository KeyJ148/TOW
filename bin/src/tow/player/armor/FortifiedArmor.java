package tow.player.armor;

import tow.Global;
import tow.player.Armor;
import tow.player.Player;

public class FortifiedArmor extends Armor {
	
	public FortifiedArmor(Player player){
		super(player,Global.c_fortified);
	}
}
