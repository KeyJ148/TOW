package main.player;

import main.Global;
import main.image.Sprite;
import main.obj.Obj;

public class Box extends Obj {
	
	public int idBox;
	public int typeBox;
	
	public Box(double x, double y, int idBox, Sprite s) {
		super(x, y, 0.0, 90.0, 1, false, s);
		//Скорость, угол, глубина, динаммческая маска, спрайт
		
		this.idBox = idBox;
	}

	public void collisionPlayer(){
		Global.clientSend.send13(idBox);
		destroy();
	}
}
