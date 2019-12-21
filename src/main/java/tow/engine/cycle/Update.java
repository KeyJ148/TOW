package tow.engine.cycle;

import tow.engine2.Global;
import tow.engine2.Loader;
import tow.engine.image.Camera;
import tow.engine2.io.Logger;

public class Update {
	
	public void loop(long delta){
		Global.game.update(delta);//Обновить главный игровой класс при необходимости

		Global.engine.render.clearTitle();//Убрать все надписи с прошлого рендера
		Global.tcpRead.update();//Обработать все полученные сообщения по TCP
		Global.udpRead.update();//Обработать все полученные сообщения по UDP

		if (Global.room != null) {
			Global.room.update(delta);//Обновить все объекты в комнате
		} else {
			Logger.println("No create room! (Global.room)", Logger.Type.ERROR);
			Loader.exit();
		}

		Camera.calc();//Расчёт положения камеры
	}

}
