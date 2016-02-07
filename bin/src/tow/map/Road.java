package tow.map;

import tow.image.Sprite;
import tow.obj.ObjLight;

public class Road extends ObjLight{
	
	public Road(double x, double y, double direction, Sprite sprite){
		super(x,y,direction,2,sprite);
	}
	
}