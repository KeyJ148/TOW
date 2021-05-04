package tow.game.client.map;

import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Position;

import java.util.Arrays;

public abstract class MapObject extends GameObject {

	private final int id;
	private final String type;

	public MapObject(int id, int x, int y, int z, String type) {
		super(Arrays.asList(new Position(x, y, z, 0)));
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