package tow.game.client;

import tow.engine.implementation.GameInterface;
import tow.game.client.map.factory.MapObjectCreatorsLoader;
import tow.game.client.menu.locations.*;

public class Game implements GameInterface {

	@Override
	public void init() {
		GameSetting.init();

		ClientData.menuLocationStorage.registry(new MainMenuLocation());
		ClientData.menuLocationStorage.registry(new PlayMenuLocation());
		ClientData.menuLocationStorage.registry(new SettingsMenuLocation());
		ClientData.menuLocationStorage.registry(new ConnectMenuLocation());
		ClientData.menuLocationStorage.registry(new ConnectByIPMenuLocation());
		ClientData.menuLocationStorage.registry(new ListOfServersMenuLocation());

		MapObjectCreatorsLoader.load();

		//TODO в конфиг или параметр (для дебага на линуксе)
		//Global.location.getMouse().getCursor().setCapture(true);
		//Global.location.getMouse().getCursor().setTexture(Global.spriteStorage.getSprite("cursor_aim_1").getTexture());

		//ServerLoader.mapPath = "maps/town100k.maptest";

		ClientData.menuLocationStorage.getMenuLocation(MainMenuLocation.class).activate();
	}

	@Override
	public void update(long delta){ }

	@Override
	public void render(){ }
}
