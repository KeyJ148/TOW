package tow.game.client.map;

import tow.engine.image.TextureHandler;
import tow.engine.obj.Obj;
import tow.engine.obj.components.Position;
import tow.engine.obj.components.render.Sprite;

import java.util.Arrays;

public class MapObject extends Obj {

	public int mid;
	
	public MapObject(double x, double y, double direction, TextureHandler textureHandler, int mid){
		super(Arrays.asList(new Position(x, y, textureHandler.depth, (int) direction), new Sprite(textureHandler)));
		this.mid = mid;
	}
}