package game.client.person.equipment.gun;

import engine.image.TextureManager;
import game.client.person.player.Gun;

public class GMass extends Gun {

	public GMass(){
		super(TextureManager.getTexture("g_mass"));
	}
}