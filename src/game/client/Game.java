package game.client;

import engine.Global;
import engine.map.Room;
import game.client.login.gui.LoginWindow;

public class Game {

	public void init() {
		//Engine: Инициализация игры перед запуском главного цикла
        Global.room = new Room(700, 500);
        new LoginWindow();

        GameSetting.init();
	}

	public void update(long delta){
		//Engine: Выполняется каждый степ перед обновлением всех игровых объектов
	}
	
	public void render(){
		//Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов
	}
}
