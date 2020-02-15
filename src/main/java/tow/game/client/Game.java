package tow.game.client;

import tow.engine.Global;
import tow.engine.image.TextureManager;
import tow.engine.implementation.GameInterface;
import tow.engine.map.Room;
import tow.engine.net.client.Connector;
import tow.game.client.lobby.StartServerListener;
import tow.game.client.menu.MenuMainRoom;
import tow.game.server.ServerLoader;


public class Game implements GameInterface {

	@Override
	public void init() {
		GameSetting.init();

        Room room = new Room(700, 500);
        room.activate();
		System.out.println(room);

        //new LoginWindow();


		ClientData.name = "Key_J";
		ServerLoader.mapPath = ClientData.map;


		Global.mouse.setCaptureCursor(true);
		Global.mouse.setCursorTexture(TextureManager.getTexture("cursor_aim_1"));

        new MenuMainRoom();
	}

	@Override
	public void update(long delta){}

	@Override
	public void render(){ }
}
