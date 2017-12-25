package game.client.person.equipment.bullet;

import engine.image.TextureManager;
import game.client.person.player.Bullet;

public class BPatron extends Bullet {

	public BPatron(){
		super(TextureManager.getTexture("b_patron"));
	}
}

