package tow.engine.map;

import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Collision;
import tow.engine.gameobject.components.Position;
import tow.engine.resources.masks.MaskLoader;

public class Border extends GameObject {
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private static final int size = 100;//Размер толщины каждой линии, ибо маска объекта должна попасть внутрь

	public Border(int roomWidth, int roomHeight, int type) {
		int x, y, w, h;
		
		switch (type){
			case NORTH: 
				x = roomWidth/2;
				y = -size/2;
				w = roomWidth;
				h = size;
			break;
			case EAST: 
				x = roomWidth+size/2;
				y = roomHeight/2;
				w = size;
				h = roomHeight;
			break;
			case SOUTH: 
				x = roomWidth/2;
				y = roomHeight+size/2;
				w = roomWidth;
				h = size;
			break;
			case WEST: 
				x = -size/2;
				y = roomHeight/2;
				w = size;
				h = roomHeight;
			break;
			default:
				x = 0;
				y = 0;
				w = 1;
				h = 1;
			break;
		}
		
		setComponent(new Position(x, y, 0, 0));

		//Путь должен быть, иначе mask выкинет ошибку при парсе; height и width наоборот -- магия
		setComponent(new Collision(MaskLoader.createDefaultMask(h, w)));
	}
	
	public static void createAll(Location location){
		location.objAdd(new Border(location.width, location.height, Border.NORTH));
		location.objAdd(new Border(location.width, location.height, Border.EAST));
		location.objAdd(new Border(location.width, location.height, Border.SOUTH));
		location.objAdd(new Border(location.width, location.height, Border.WEST));
	}

}
