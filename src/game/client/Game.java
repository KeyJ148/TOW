package game.client;

import engine.Global;
import engine.image.TextureManager;
import engine.implementation.GameInterface;
import engine.io.MouseHandler;
import engine.map.Room;
import engine.obj.components.render.Sprite;
import game.client.login.gui.LoginWindow;

public class Game implements GameInterface {

	@Override
	public void init() {
        Global.room = new Room(700, 500);
        new LoginWindow();

        GameSetting.init();
		MouseHandler.cursor.rendering = new Sprite(MouseHandler.cursor, TextureManager.getTexture("cursor_aim_1"));
	}

	@Override
	public void update(long delta){ }

	@Override
	public void render(){ }
}
