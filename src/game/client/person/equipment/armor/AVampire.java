package game.client.person.equipment.armor;

import engine.image.TextureManager;
import game.client.person.player.Armor;

public class AVampire extends Armor {

	public AVampire(){
		super(TextureManager.getAnimation("a_vampire"));
	}
}
