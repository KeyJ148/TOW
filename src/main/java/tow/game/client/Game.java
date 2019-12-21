package tow.game.client;

import tow.engine2.Global;
import tow.engine.image.TextureManager;
import tow.engine2.implementation.GameInterface;
import tow.engine.io.MouseHandler;
import tow.engine.map.Room;
import tow.engine.obj.components.render.Sprite;
import tow.game.client.login.gui.LoginWindow;

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
