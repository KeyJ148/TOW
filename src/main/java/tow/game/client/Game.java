package tow.game.client;

import tow.engine.Global;
import tow.engine.gui.CachedGuiPanel;
import tow.engine.image.Color;
import tow.engine.implementation.GameInterface;
import tow.engine.map.Background;
import tow.engine.map.Location;
import tow.engine.services.CachedGuiElementService;
import tow.game.client.map.factory.MapObjectCreatorsLoader;
import tow.game.client.menu.guipanels.ConnectMenuGuiPanel;
import tow.game.client.menu.guipanels.CreateGameMenuGuiPanel;
import tow.game.client.menu.guipanels.MainMenuGuiPanel;

public class Game implements GameInterface {

	@Override
	public void init() {
		GameSetting.init();

		Global.cachedGuiPanelStorage.registry(new MainMenuGuiPanel());
		Global.cachedGuiPanelStorage.registry(new ConnectMenuGuiPanel());
		/*Global.guiPanelStorage.registry(new SettingsMenuLocation());
		Global.guiPanelStorage.registry(new ConnectMenuLocation());
		Global.guiPanelStorage.registry(new ConnectByIPMenuLocation());
		Global.guiPanelStorage.registry(new ListOfServersMenuLocation());*/
		Global.cachedGuiPanelStorage.registry(new CreateGameMenuGuiPanel());


		MapObjectCreatorsLoader.load();

		//TODO в конфиг или параметр (для дебага на линуксе)
		//Global.location.getMouse().getCursor().setCapture(true);
		//Global.location.getMouse().getCursor().setTexture(Global.spriteStorage.getSprite("cursor_aim_1").getTexture());

		//ServerLoader.mapPath = "maps/town100k.maptest";

		Location location = new Location(Global.engine.render.getWidth(), Global.engine.render.getHeight());
		location.background = new Background(Color.GRAY, Color.GRAY);
		location.activate();

		CachedGuiPanel cachedGuiPanel = Global.cachedGuiPanelStorage.getPanel(MainMenuGuiPanel.class);
		new CachedGuiElementService().addCachedComponentToLocation(cachedGuiPanel,
				Global.engine.render.getWidth() / 2,
				Global.engine.render.getHeight() / 2,
				location);
	}

	@Override
	public void update(long delta){ }

	@Override
	public void render(){ }
}
