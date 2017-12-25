package game.client.person.equipment.bullet;

import engine.image.TextureManager;
import game.client.person.player.Bullet;

public class BDefault extends Bullet {
	
	public BDefault(){
		super(TextureManager.getTexture("b_default"));
	}
}

