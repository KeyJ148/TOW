package tow.game.client.map;

import tow.engine.image.TextureHandler;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.SpriteRender;

import java.util.Arrays;

public class MapObject extends GameObject {

	public int mid;
	
	public MapObject(double x, double y, double direction, TextureHandler textureHandler, int mid){
		super(Arrays.asList(new Position(x, y, textureHandler.depth, (int) direction), new SpriteRender(textureHandler)));
		this.mid = mid;
	}
}