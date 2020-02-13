package tow.game.client;

import tow.engine.Global;
import tow.engine.image.TextureManager;
import tow.engine.implementation.GameInterface;
import tow.engine.map.Room;
import tow.engine.net.client.Connector;
import tow.game.client.lobby.StartServerListener;
import tow.game.server.ServerLoader;


public class Game implements GameInterface, StartServerListener {



	@Override
	public void init() {
        Global.room = new Room(700, 500);
        //new LoginWindow();

		ClientData.name = "Key_J";
		ServerLoader.startServerListener = this;
		ServerLoader.mapPath = ClientData.map;
		new ServerLoader(25566, 1, false);

		//new Connector().connect("127.0.0.1", 25566);

		GameSetting.init();

		Global.mouse.setCaptureCursor(true);
        Global.mouse.setCursorTexture(TextureManager.getTexture("cursor_aim_1"));
	}

	@Override
	public void update(long delta){ }

	@Override
	public void render(){ }

	@Override
	public void serverStart() {
		new Connector().connect("127.0.0.1", 25566);
	}
}
