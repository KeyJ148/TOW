package main.player.armor;

import main.Global;
import main.player.Armor;
import main.player.Player;

public class FortifiedArmor extends Armor {
	
	public FortifiedArmor(Player player){
		super(player,Global.c_fortified);
	}
}
