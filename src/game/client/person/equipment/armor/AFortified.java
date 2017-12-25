package game.client.person.equipment.armor;

import engine.image.TextureManager;
import game.client.person.player.Armor;

public class AFortified extends Armor {

	public AFortified(){
		super(TextureManager.getAnimation("a_fortified"));
	}
}
