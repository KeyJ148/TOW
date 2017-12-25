package game.client.person.equipment.gun;

import engine.image.TextureManager;
import game.client.person.player.Gun;

public class GPower extends Gun {

	public GPower(){
		super(TextureManager.getTexture("g_power"));
	}
}