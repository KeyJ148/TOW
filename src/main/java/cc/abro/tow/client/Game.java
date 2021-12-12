package cc.abro.tow.client;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.implementation.GameInterface;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.resources.settings.SettingsLoader;
import cc.abro.orchengine.resources.settings.SettingsStorageHandler;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.map.factory.MapObjectCreatorsLoader;
import cc.abro.tow.client.menu.MenuLocation;
import cc.abro.tow.client.menu.panels.*;
import cc.abro.tow.client.services.ConnectServerService;
import cc.abro.tow.client.services.CreateServerService;
import cc.abro.tow.client.services.SettingsService;

import java.io.IOException;
import java.util.List;

public class Game implements GameInterface {

    private final GuiPanelStorage guiPanelStorage;
    private final LocationManager locationManager;

    public Game(GuiPanelStorage guiPanelStorage, LocationManager locationManager) {
        this.guiPanelStorage = guiPanelStorage;
        this.locationManager = locationManager;
    }

    @Override
    public List<Class<?>> getInitializingServices() {
        return List.of(GuiService.class,
                SettingsService.class,
                CreateServerService.class,
                ConnectServerService.class);
    }

    @Override
    public void init() {
        GameSetting.init();
        //TODO переделать GameSetting под JSON, и вынести инициализацию настроек ниже в отдельный класс
        try {
            SettingsStorage.GRAPHICS = SettingsStorageHandler.initExternalSettingsOrDefaultFromInternal(SettingsStorage.Graphics.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean settingsLoadSuccess = true;
        try {
            SettingsStorage.PROFILE = SettingsLoader.loadExternalSettings(SettingsStorage.Profile.class);
        } catch (IOException e) {
            settingsLoadSuccess = false;
            try {
                SettingsStorage.PROFILE = SettingsLoader.loadInternalSettings(SettingsStorage.Profile.class);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
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

        locationManager.setActiveLocation(new MenuLocation(settingsLoadSuccess));
    }

    @Override
    public void update(long delta) {
    }

    @Override
    public void render() {
    }
}
