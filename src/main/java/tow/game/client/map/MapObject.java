package tow.game.client.map;

import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.SpriteRender;
import tow.engine.resources.textures.Texture;

import java.util.Arrays;

public class MapObject extends GameObject {

	public int mid;
	
	public MapObject(double x, double y, double direction, Texture texture, int mid){
		super(Arrays.asList(new Position(x, y, textureHandler.depth, (int) direction), new SpriteRender(texture)));
		this.mid = mid;
	}
}