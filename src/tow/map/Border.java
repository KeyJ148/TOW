package tow.map;

import tow.Global;
import tow.image.Mask;
import tow.image.TextureManager;
import tow.obj.Obj;

public class Border extends Obj {
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private static final int size = 100;//Размер толщины каждой линии, ибо маска танка должна попасть внутрь

	public Border(int type) {
		super(0, 0, 0, 0, 0, false, TextureManager.player_sys);
		
		int x, y, w, h;
		switch (type){
			case NORTH: 
				x = Global.widthMap/2;
				y = -size/2;
				w = Global.widthMap;
				h = size;
			break;
			case EAST: 
				x = Global.widthMap+size/2;
				y = Global.heightMap/2;
				w = size;
				h = Global.heightMap;
			break;
			case SOUTH: 
				x = Global.widthMap/2;
				y = Global.heightMap+size/2;
				w = Global.widthMap;
				h = size;
			break;
			case WEST: 
				x = -size/2;
				y = Global.heightMap/2;
				w = size;
				h = Global.heightMap;
			break;
			default:
				x = 0;
				y = 0;
				w = 1;
				h = 1;
			break;
		}
		
		setX(x);
		setY(y);
		
		mask = new Mask("mask.png", h, w);//Путь должен быть, иначе mask выкинет ошибку при парсе; height и width наоборот -- магия
		mask.calc(x, y, 0);
		mask.dynamic = false;
	}
	
	public static void createAll(){
		new Border(Border.NORTH);
		new Border(Border.EAST);
		new Border(Border.SOUTH);
		new Border(Border.WEST);
	}

}
