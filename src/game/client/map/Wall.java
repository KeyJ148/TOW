package game.client.map;

import engine.image.TextureHandler;
import engine.obj.Obj;
import engine.obj.components.Collision;

public class Wall extends Obj {
	
	public Wall(double x, double y, double direction, TextureHandler textureHandler){
		super(x, y, direction, textureHandler);

		collision = new Collision(this, textureHandler.mask);
	}
	
}