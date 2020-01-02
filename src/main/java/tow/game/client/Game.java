package tow.game.client;

import tow.engine.Global;
import tow.engine3.image.TextureManager;
import tow.engine2.implementation.GameInterface;
import tow.engine2.map.Room;
import tow.game.client.login.gui.LoginWindow;

public class Game implements GameInterface {

	@Override
	public void init() {
        Global.room = new Room(700, 500);
        new LoginWindow();

        GameSetting.init();
		Global.mouse.setCaptureCursor(true);
        Global.mouse.setCursorTexture(TextureManager.getTexture("cursor_aim_1"));
	}

	@Override
	public void update(long delta){ }

	@Override
	public void render(){ }
}
