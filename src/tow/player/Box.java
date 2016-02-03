package tow.player;

import tow.Global;
import tow.image.Sprite;
import tow.obj.Obj;

public class Box extends Obj {
	
	public int idBox;
	public int typeBox;
	public boolean collision = false;//Произошло ли столкновение?
	
	public Box(double x, double y, int idBox, Sprite s) {
		super(x, y, 0.0, 90.0, 1, false, s);
		//Скорость, угол, глубина, динаммческая маска, спрайт
		
		this.idBox = idBox;
	}

	public void collisionPlayer(){
		collision = true;
		destroy();
		Global.clientSend.send13(idBox);
	}
}
