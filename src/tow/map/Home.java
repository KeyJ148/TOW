package tow.map;

import tow.image.TextureHandler;
import tow.obj.Obj;

public class Home extends Obj{
	
	public Home(double x, double y, double direction, TextureHandler textureHandler){
		super(x,y,0.0,direction,1,false,textureHandler);
	}
	
}