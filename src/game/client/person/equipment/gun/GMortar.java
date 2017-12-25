package game.client.person.equipment.gun;

import engine.image.TextureManager;
import game.client.person.player.Gun;

public class GMortar extends Gun {

	public GMortar(){
		super(TextureManager.getTexture("g_mortar"));
	}
}