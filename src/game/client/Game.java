package game.client;

import engine.Global;
import engine.map.Room;
import engine.setting.ConfigReader;
import game.client.login.gui.LoginWindow;

public class Game {

	public static final String SETTING_NAME = "game/main.properties";

	public void init() {
		//Engine: Инициализация игры перед запуском главного цикла
        Global.room = new Room(700, 500);
        new LoginWindow();

		ConfigReader cr = new ConfigReader(SETTING_NAME);
		ClientData.MPS = cr.findInteger("MPS");
	}

	public void update(long delta){
		//Engine: Выполняется каждый степ перед обновлением всех игровых объектов
	}
	
	public void render(){
		//Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов
	}
}
