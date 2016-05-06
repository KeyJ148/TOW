package tow.map;

import tow.image.TextureHandler;
import tow.obj.Obj;

public class Tree extends Obj {

	public Tree(double x, double y, double direction, TextureHandler textureHandler) {
		super(x, y, 0, direction,-2, false, textureHandler);
	}

}
