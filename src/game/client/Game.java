package game.client;

import engine.Global;
import engine.io.KeyboardHandler;
import engine.map.Room;
import game.client.login.gui.LoginWindow;
import org.lwjgl.input.Keyboard;

public class Game {

	public void init() {
		//Engine: Инициализация игры перед запуском главного цикла
        Global.room = new Room(700, 500);
        new LoginWindow();
    }

    long last = System.currentTimeMillis();

	public void update(long delta){
		//Engine: Выполняется каждый степ перед обновлением всех игровых объектов
		if (KeyboardHandler.isKeyDown(Keyboard.KEY_ESCAPE)) System.exit(0);
	}
	
	public void render(){
		//Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов
	}
}
