package main.player;

import main.Global;
import main.obj.Obj;

public class Box extends Obj {
	
	public int idBox;

	public Box(double x, double y, int idBox) {
		super(x, y, 0.0, 90.0, 1, false, Global.box);
		//Скорость, угол, глубина, динаммческая маска, спрайт
		
		this.idBox = idBox;
	}

	public void collisionPlayer(){
		Global.clientSend.send13(idBox);
		destroy();
	}
}
