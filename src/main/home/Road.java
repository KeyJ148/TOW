package main.home;

import main.image.Sprite;
import main.obj.ObjLight;

public class Road extends ObjLight{
	
	public Road(double x, double y, double direction, Sprite sprite){
		super(x,y,direction,2,sprite);
	}
	
}