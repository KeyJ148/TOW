package cc.abro.tow.client;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.init.interfaces.GameInterface;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.resources.settings.SettingsLoader;
import cc.abro.orchengine.resources.settings.SettingsStorageHandler;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.factory.MapObjectCreatorsLoader;
import cc.abro.tow.client.menu.MenuLocation;
import cc.abro.tow.client.menu.panels.ConnectByIPMenuGuiPanel;
import cc.abro.tow.client.menu.panels.CreateGameMenuGuiPanel;
import cc.abro.tow.client.menu.panels.ListOfServersMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;

import java.io.IOException;

@GameService
public class Game implements GameInterface {

    private final GuiPanelStorage guiPanelStorage;
    private final LocationManager locationManager;
    private final ClientData clientData;

    public Game(GuiPanelStorage guiPanelStorage, LocationManager locationManager, ClientData clientData) {
        this.guiPanelStorage = guiPanelStorage;
        this.locationManager = locationManager;
        this.clientData = clientData;
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
            Context.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getMouse().getCursor().setCapture(true);
            Texture texture = Context.getService(SpriteStorage.class).getSprite(SettingsStorage.GRAPHICS.CURSOR_SPRITE).getTexture();
            Context.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getMouse().getCursor().setTexture(texture);
        }
        clientData.name = SettingsStorage.PROFILE.NICKNAME;
        clientData.color = new Color(SettingsStorage.PROFILE.COLOR);

        MapObjectCreatorsLoader.load();


        guiPanelStorage.registry(new MainMenuGuiPanel());
        guiPanelStorage.registry(new ConnectByIPMenuGuiPanel());
        guiPanelStorage.registry(new ListOfServersMenuGuiPanel());
        guiPanelStorage.registry(new CreateGameMenuGuiPanel());

        //TODO ServerLoader.mapPath = "maps/town10k.maptest";

        locationManager.setActiveLocation(new MenuLocation(settingsLoadSuccess));
    }
}
