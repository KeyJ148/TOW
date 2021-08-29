package cc.abro.tow.client;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.implementation.GameInterface;
import cc.abro.orchengine.resources.settings.SettingsStorageHandler;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.factory.MapObjectCreatorsLoader;
import cc.abro.tow.client.menu.MenuLocation;
import cc.abro.tow.client.menu.panels.gui.*;

import java.io.IOException;

public class Game implements GameInterface {

    private final GuiPanelStorage guiPanelStorage;

    public Game(GuiPanelStorage guiPanelStorage){
        this.guiPanelStorage = guiPanelStorage;
    }

    @Override
    public void init() {
        GameSetting.init();
        //TODO переделать GameSetting под JSON, и вынести инициализацию настроек ниже в отдельный класс
        try {
            SettingsStorage.GRAPHICS = SettingsStorageHandler.initExternalSettingsOrDefaultFromInternal(SettingsStorage.Graphics.class);
            SettingsStorage.PROFILE = SettingsStorageHandler.initExternalSettingsOrDefaultFromInternal(SettingsStorage.Profile.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (SettingsStorage.GRAPHICS.CURSOR_SPRITE != null) {
            Global.location.getMouse().getCursor().setCapture(true);
            Texture texture = Manager.getService(SpriteStorage.class).getSprite(SettingsStorage.GRAPHICS.CURSOR_SPRITE).getTexture();
            Global.location.getMouse().getCursor().setTexture(texture);
        }

        MapObjectCreatorsLoader.load();

        guiPanelStorage.registry(new MainMenuGuiPanel());
        guiPanelStorage.registry(new SettingsMenuGuiPanel());
        guiPanelStorage.registry(new ConnectByIPMenuGuiPanel());
        guiPanelStorage.registry(new ListOfServersMenuGuiPanel());
        guiPanelStorage.registry(new CreateGameMenuGuiPanel());

        //TODO ServerLoader.mapPath = "maps/town10k.maptest";

        new MenuLocation().activate();
    }

    @Override
    public void update(long delta) {
    }

    @Override
    public void render() {
    }
}
