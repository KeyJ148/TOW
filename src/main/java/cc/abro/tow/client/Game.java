package cc.abro.tow.client;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gui.CachedGuiPanel;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.implementation.GameInterface;
import cc.abro.orchengine.map.Background;
import cc.abro.orchengine.map.Location;
import cc.abro.orchengine.services.CachedGuiElementService;
import cc.abro.tow.client.map.factory.MapObjectCreatorsLoader;
import cc.abro.tow.client.menu.guipanels.*;

public class Game implements GameInterface {

    @Override
    public void init() {
        GameSetting.init();

        Global.cachedGuiPanelStorage.registry(new MainMenuGuiPanel());
        Global.cachedGuiPanelStorage.registry(new SettingsMenuGuiPanel());
        Global.cachedGuiPanelStorage.registry(new ConnectMenuGuiPanel());
        Global.cachedGuiPanelStorage.registry(new ConnectByIPMenuGuiPanel());
        Global.cachedGuiPanelStorage.registry(new ListOfServersMenuGuiPanel());
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
        new CachedGuiElementService().addCachedComponentToLocationShiftedToCenter(cachedGuiPanel,
                Global.engine.render.getWidth() / 2,
                Global.engine.render.getHeight() / 2,
                location);
    }

    @Override
    public void update(long delta) {
    }

    @Override
    public void render() {
    }
}
