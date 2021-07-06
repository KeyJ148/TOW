package cc.abro.tow.client;

import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.implementation.GameInterface;
import cc.abro.tow.client.map.factory.MapObjectCreatorsLoader;
import cc.abro.tow.client.menu.MenuLocation;
import cc.abro.tow.client.menu.panels.gui.*;

public class Game implements GameInterface {

    private final GuiPanelStorage guiPanelStorage;

    public Game(GuiPanelStorage guiPanelStorage){
        this.guiPanelStorage = guiPanelStorage;
    }

    @Override
    public void init() {
        GameSetting.init();

        guiPanelStorage.registry(new MainMenuGuiPanel());
        guiPanelStorage.registry(new SettingsMenuGuiPanel());
        guiPanelStorage.registry(new ConnectMenuGuiPanel());
        guiPanelStorage.registry(new ConnectByIPMenuGuiPanel());
        guiPanelStorage.registry(new ListOfServersMenuGuiPanel());
        guiPanelStorage.registry(new CreateGameMenuGuiPanel());


        MapObjectCreatorsLoader.load();

        //TODO в конфиг или параметр (для дебага на линуксе)
        //Global.location.getMouse().getCursor().setCapture(true);
        //Global.location.getMouse().getCursor().setTexture(Manager.getService(SpriteStorage.class).getSprite("cursor_aim_1").getTexture());

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
