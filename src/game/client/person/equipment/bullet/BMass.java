package game.client.person.equipment.bullet;

import engine.image.TextureManager;
import game.client.person.player.Bullet;

public class BMass extends Bullet {

	public BMass(){
		super(TextureManager.getTexture("b_mass"));
	}
}

