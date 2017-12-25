package game.client.person.equipment.bullet;

import engine.image.TextureManager;
import game.client.person.player.Bullet;

public class BSteel extends Bullet {

	public BSteel(){
		super(TextureManager.getTexture("b_steel"));
	}
}

