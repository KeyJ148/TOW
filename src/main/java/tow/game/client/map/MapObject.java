package tow.game.client.map;

import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.SpriteRender;
import tow.engine.resources.textures.Texture;

import java.util.Arrays;
import java.util.Map;

public class MapObject extends GameObject {

	public int id;
	
	public MapObject(double x, double y, double direction, Texture texture, int id){
		super(Arrays.asList(new Position(x, y, textureHandler.depth, (int) direction), new SpriteRender(texture)));
		this.id = id;
	}

	public MapObject(int id, double x, double y, double direction, String type, Map<String, Object> parameters) {

	}
}