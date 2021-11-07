package cc.abro.tow.client;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.implementation.GameInterface;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.resources.settings.SettingsStorageHandler;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.factory.MapObjectCreatorsLoader;
import cc.abro.tow.client.menu.MenuLocation;
import cc.abro.tow.client.menu.panels.gui.ConnectByIPMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.CreateGameMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.ListOfServersMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;

import java.io.IOException;

public class Game implements GameInterface {

    private final GuiPanelStorage guiPanelStorage;
    private final LocationManager locationManager;

    public Game(GuiPanelStorage guiPanelStorage, LocationManager locationManager){
        this.guiPanelStorage = guiPanelStorage;
        this.locationManager = locationManager;
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
            Manager.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getMouse().getCursor().setCapture(true);
            Texture texture = Manager.getService(SpriteStorage.class).getSprite(SettingsStorage.GRAPHICS.CURSOR_SPRITE).getTexture();
            Manager.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getMouse().getCursor().setTexture(texture);
        }
        ClientData.name = SettingsStorage.PROFILE.NICKNAME;
        ClientData.color = new Color(SettingsStorage.PROFILE.COLOR);

        MapObjectCreatorsLoader.load();

        guiPanelStorage.registry(new MainMenuGuiPanel());
        guiPanelStorage.registry(new ConnectByIPMenuGuiPanel());
        guiPanelStorage.registry(new ListOfServersMenuGuiPanel());
        guiPanelStorage.registry(new CreateGameMenuGuiPanel());

        //TODO ServerLoader.mapPath = "maps/town10k.maptest";

        locationManager.setActiveLocation(new MenuLocation());
    }

    @Override
    public void update(long delta) {
    }

    @Override
    public void render() {
    }
}
