package tow.game.client.map;

import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.SpriteRender;

import java.util.Arrays;
import java.util.Map;

public abstract class MapObject extends GameObject {

	private final int id;
	private final String type;

	public MapObject(int id, int x, int y, int z, String type, Map<String, Object> parameters) {
		super(Arrays.asList(new Position(x, y, -z, (int) direction), new SpriteRender(texture)));
		//TODO: поменять -z на z после замены в position и далее depth на z
		this.id = id;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public String getType(){
		return type;
	}
}