package game.client.person.equipment.gun;

import engine.image.TextureManager;
import game.client.person.player.Gun;

public class GDefault extends Gun {
	
	public GDefault(){
		super(TextureManager.getTexture("g_default"));
	}
}