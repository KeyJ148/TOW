package game.client.map;

import engine.image.TextureHandler;
import engine.obj.Obj;

public class MapObject extends Obj {

	public int mid;
	
	public MapObject(double x, double y, double direction, TextureHandler textureHandler, int mid){
		super(x, y, direction, textureHandler);
		this.mid = mid;
	}
}