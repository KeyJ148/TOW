package game.client;

import engine.Global;
import engine.image.TextureManager;
import engine.io.MouseHandler;
import engine.map.Room;
import engine.obj.components.render.Sprite;
import game.client.login.gui.LoginWindow;

public class Game {

	public void init() {
		//Engine: Инициализация игры перед запуском главного цикла
        Global.room = new Room(700, 500);
        new LoginWindow();

        GameSetting.init();
		MouseHandler.cursor.rendering = new Sprite(MouseHandler.cursor, TextureManager.getTexture("cursor_aim_1"));
	}

	public void update(long delta){
		//Engine: Выполняется каждый степ перед обновлением всех игровых объектов
	}
	
	public void render(){
		//Engine: Выполняется каждый степ перед перерисовкой всех игровых объектов
	}
}
