package game.client.person.equipment.armor;

import engine.image.TextureManager;
import game.client.person.player.Armor;

public class AFury extends Armor {

	public AFury(){
		super(TextureManager.getAnimation("a_fury"));
	}
}
