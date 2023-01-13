package cc.abro.tow.client;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.init.interfaces.GameInterface;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.resources.JsonContainerLoader;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.factory.MapObjectCreatorsLoader;
import cc.abro.tow.client.menu.MenuLocation;
import cc.abro.tow.client.menu.panels.ConnectByIPMenuGuiPanel;
import cc.abro.tow.client.menu.panels.CreateGameMenuGuiPanel;
import cc.abro.tow.client.menu.panels.ListOfServersMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.settings.Settings;
import cc.abro.tow.client.settings.SettingsService;
import cc.abro.tow.server.ServerLoader;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Arrays;

@Log4j2
@GameService
public class Game implements GameInterface {

    public static final String SPRITE_CONFIG_PATH = "configs/sprite.json";
    private static final String WINDOW_NAME = "Tanks: Orchestra of war";

    private final GuiPanelStorage guiPanelStorage;
    private final LocationManager locationManager;
    private final ClientData clientData;
    private final AudioService audioService;
    private final SettingsService settingsService;

    public Game(GuiPanelStorage guiPanelStorage, LocationManager locationManager, ClientData clientData,
                AudioService audioService, SettingsService settingsService) {
        this.guiPanelStorage = guiPanelStorage;
        this.locationManager = locationManager;
        this.clientData = clientData;
        this.audioService = audioService;
        this.settingsService = settingsService;
    }

    @Override
    public void init() {
        GameSetting.init();
        audioService.setVolume(settingsService.getSettings().getVolume().getSoundVolume());

        try {
            SpriteStorage.SpriteContainer[] spriteContainers = JsonContainerLoader.loadInternalFile(
                    SpriteStorage.SpriteContainer[].class, SPRITE_CONFIG_PATH);
            Context.getService(SpriteStorage.class).loadSprites(Arrays.stream(spriteContainers).toList());
        } catch (IOException e) {
            log.fatal("Error loading sprites", e);
            throw new RuntimeException(e);
        }

        if (settingsService.getSettings().getGraphics().getCursorSprite() != null) {
            Texture texture = Context.getService(SpriteStorage.class).getSprite(settingsService.getSettings().getGraphics().getCursorSprite()).texture();
            Context.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getMouse().getCursor().setTexture(texture);
        }
        clientData.name = settingsService.getSettings().getProfile().getNickname();
        clientData.color = new Color(settingsService.getSettings().getProfile().getColor());

        Texture icon = Context.getService(SpriteStorage.class).getSprite("window_icon").texture();
        Context.getService(Render.class).setIcon(icon);

        MapObjectCreatorsLoader.load();

        guiPanelStorage.registry(new MainMenuGuiPanel());
        guiPanelStorage.registry(new ConnectByIPMenuGuiPanel());
        guiPanelStorage.registry(new ListOfServersMenuGuiPanel());
        guiPanelStorage.registry(new CreateGameMenuGuiPanel());

        ServerLoader.mapPath = "maps/town10k.maptest";

        locationManager.setActiveLocation(new MenuLocation(!settingsService.isLoadSuccess()));
    }

    @Override
    public Render.Settings getRenderSettings() {
        Settings.Graphics graphics = settingsService.getSettings().getGraphics();
        return new Render.Settings(graphics.getWidthScreen(), graphics.getHeightScreen(), graphics.isFullScreen(),
                graphics.getFpsLimit(), graphics.getVSyncDivider(), WINDOW_NAME);
    }
}