package cc.abro.tow.client;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.implementation.GameInterface;
import cc.abro.tow.client.map.factory.MapObjectCreatorsLoader;
import cc.abro.tow.client.menu.MenuLocation;
import cc.abro.tow.client.menu.panels.gui.*;

public class Game implements GameInterface {

    @Override
    public void init() {
        GameSetting.init();

        Global.guiPanelStorage.registry(new MainMenuGuiPanel());
        Global.guiPanelStorage.registry(new SettingsMenuGuiPanel());
        Global.guiPanelStorage.registry(new ConnectMenuGuiPanel());
        Global.guiPanelStorage.registry(new ConnectByIPMenuGuiPanel());
        Global.guiPanelStorage.registry(new ListOfServersMenuGuiPanel());
        Global.guiPanelStorage.registry(new CreateGameMenuGuiPanel());


        MapObjectCreatorsLoader.load();

        //TODO в конфиг или параметр (для дебага на линуксе)
        //Global.location.getMouse().getCursor().setCapture(true);
        //Global.location.getMouse().getCursor().setTexture(Global.spriteStorage.getSprite("cursor_aim_1").getTexture());

        //ServerLoader.mapPath = "maps/town100k.maptest";

        new MenuLocation().activate();
    }

    @Override
    public void update(long delta) {
    }

    @Override
    public void render() {
    }
}
