package tow.game.client;

import tow.engine.implementation.GameInterface;
import tow.game.client.map.factory.MapObjectCreatorsLoader;
import tow.game.client.menu.MainMenuLocation;

public class Game implements GameInterface {

	@Override
	public void init() {
		GameSetting.init();

		//TODO в конфиг или параметр (для дебага на линуксе)
		//Global.location.getMouse().getCursor().setCapture(true);
		//Global.location.getMouse().getCursor().setTexture(Global.spriteStorage.getSprite("cursor_aim_1").getTexture());

		//ServerLoader.mapPath = "maps/town100k.maptest";
		MapObjectCreatorsLoader.load();
        new MainMenuLocation();
	}

	@Override
	public void update(long delta){ }

	@Override
	public void render(){ }
}
