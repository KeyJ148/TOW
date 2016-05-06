package tow.map;

import tow.image.TextureHandler;
import tow.obj.ObjLight;

public class Road extends ObjLight{
	
	public Road(double x, double y, double direction, TextureHandler textureHandler){
		super(x,y,direction,2,textureHandler);
	}
	
}