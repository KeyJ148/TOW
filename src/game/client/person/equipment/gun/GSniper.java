package game.client.person.equipment.gun;

import engine.image.TextureManager;
import game.client.person.player.Gun;

public class GSniper extends Gun {

	public GSniper(){
		super(TextureManager.getTexture("g_sniper"));
	}
}