package game.client.person.equipment.armor;

import engine.image.TextureManager;
import game.client.person.player.Armor;

public class AMite extends Armor {

	public AMite(){
		super(TextureManager.getAnimation("a_mite"));
	}
}
