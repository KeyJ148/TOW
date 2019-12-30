package tow.engine3.map;

import tow.engine3.image.Mask;
import tow.engine3.obj.Obj;
import tow.engine3.obj.components.Collision;
import tow.engine3.obj.components.Position;

public class Border extends Obj {
	
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
		
		this.position = new Position(this, x, y, 0, 0);

		//Путь должен быть, иначе mask выкинет ошибку при парсе; height и width наоборот -- магия
		this.collision = new Collision(this, new Mask("mask.png", h, w));
	}
	
	public static void createAll(Room room){
		room.objAdd(new Border(room.width, room.height, Border.NORTH));
		room.objAdd(new Border(room.width, room.height, Border.EAST));
		room.objAdd(new Border(room.width, room.height, Border.SOUTH));
		room.objAdd(new Border(room.width, room.height, Border.WEST));
	}

}
