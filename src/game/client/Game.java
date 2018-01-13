package game.client;

import engine.Global;
import engine.map.Room;
import game.client.login.gui.LoginWindow;

public class Game {

	public void init() {
		//Engine: Инициализация игры перед запуском главного цикла
        Global.room = new Room(700, 500);
        new LoginWindow();
	}

	public void update(long delta){
		//Engine: Выполняется каждый степ перед обновлением всех игровых объектов

		/*
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
			AudioStorage.getAudio("shot_tank").playAsSoundEffect(1.0f, 1.0f, false);
		}
		*/
	}
	
	public void render(){
		//Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов
	}
}
