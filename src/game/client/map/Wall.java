package game.client.map;

import engine.image.TextureHandler;
import engine.io.Logger;
import engine.obj.Obj;
import engine.obj.components.Collision;

public class Wall extends Obj {

	public int stabillity;
	
	public Wall(double x, double y, double direction, TextureHandler textureHandler){
		super(x, y, direction, textureHandler);

		collision = new Collision(this, textureHandler.mask);
		stabillity = getStabillityByType(textureHandler.type);
	}

	private int getStabillityByType(String type){
		switch (type){
			case "home": return 100;
			case "tree": return 30;
			default:
				Logger.println("This type not have stability parametr: " + type, Logger.Type.ERROR);
				return 100;
		}
	}

	public void destroyByArmor(){
		destroy();
	}
	
}