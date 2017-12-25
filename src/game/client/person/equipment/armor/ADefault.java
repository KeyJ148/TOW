package game.client.person.equipment.armor;

import engine.image.TextureManager;
import game.client.person.player.Armor;

public class ADefault extends Armor {
	
	public ADefault(){
		super(TextureManager.getAnimation("a_default"));
	}
}
