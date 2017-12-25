package game.client.person.equipment.gun;

import engine.image.TextureManager;
import game.client.person.player.Gun;

public class GVampire extends Gun {

	public GVampire(){
		super(TextureManager.getTexture("g_vampire"));
	}
}