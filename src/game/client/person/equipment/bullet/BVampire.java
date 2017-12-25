package game.client.person.equipment.bullet;

import engine.image.TextureManager;
import game.client.person.player.Bullet;

public class BVampire extends Bullet {

	public BVampire(){
		super(TextureManager.getTexture("b_vampire"));
	}
}

