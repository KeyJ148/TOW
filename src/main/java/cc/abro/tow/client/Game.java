package cc.abro.tow.client;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.init.interfaces.GameInterface;
import cc.abro.orchengine.resources.JsonContainerLoader;
import cc.abro.orchengine.resources.animations.AnimationStorage;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.orchengine.resources.locales.LocalizationService;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.GuiService;
import cc.abro.orchengine.services.LightSystemService;
import cc.abro.tow.client.gui.menu.MenuLocation;
import cc.abro.tow.client.map.factory.MapObjectFactory;
import cc.abro.tow.client.settings.DevSettingsService;
import cc.abro.tow.client.settings.Settings;
import cc.abro.tow.client.settings.SettingsService;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.armor.ArmorSpecificationStorage;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.bullet.BulletSpecificationStorage;
import cc.abro.tow.client.tanks.equipment.bulletbehaviors.BulletBehaviorsCreator;
import cc.abro.tow.client.tanks.equipment.gun.GunCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.gun.GunSpecificationStorage;
import cc.abro.tow.client.tanks.player.PlayerTank;
import cc.abro.tow.server.ServerLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Arrays;

@Log4j2
@GameService
@RequiredArgsConstructor
public class Game implements GameInterface {

    public static final String SPRITE_CONFIG_PATH = "configs/resources/sprite.json";
    public static final String ANIMATION_CONFIG_PATH = "configs/resources/animation.json";
    private static final String WINDOW_NAME = "Tanks: Orchestra of war";

    private final GuiPanelStorage guiPanelStorage;
    private final LocationManager locationManager;
    private final AudioService audioService;
    private final SettingsService settingsService;
    private final DevSettingsService devSettingsService;
    private final LocalizationService localizationService;
    private final MapObjectFactory mapObjectFactory;
    private final ArmorCreatorsStorage armorCreatorsStorage;
    private final GunCreatorsStorage gunCreatorsStorage;
    private final BulletCreatorsStorage bulletCreatorsStorage;
    private final ArmorSpecificationStorage armorSpecificationStorage;
    private final GunSpecificationStorage gunSpecificationStorage;
    private final BulletSpecificationStorage bulletSpecificationStorage;
    private final BulletBehaviorsCreator bulletBehaviorsCreator;

    @Override
    public void init() {
        audioService.setVolume(settingsService.getSettings().getVolume().getSoundVolume());

        try {
            localizationService.changeLocale(settingsService.getSettings().getProfile().getLanguage());
        } catch (Exception e) {
            log.error("Error loading Localization: " + settingsService.getSettings().getProfile().getLanguage(), e);
            localizationService.changeLocale("en");
        }

        try {
            SpriteStorage.SpriteContainer[] spriteContainers = JsonContainerLoader.loadInternalFile(
                    SpriteStorage.SpriteContainer[].class, SPRITE_CONFIG_PATH);
            Context.getService(SpriteStorage.class).loadSprites(Arrays.stream(spriteContainers).toList());
        } catch (IOException e) {
            log.fatal("Error loading sprites", e);
            throw new RuntimeException(e);
        }

        try {
            AnimationStorage.AnimationContainer[] animationContainers = JsonContainerLoader.loadInternalFile(
                    AnimationStorage.AnimationContainer[].class, ANIMATION_CONFIG_PATH);
            Context.getService(AnimationStorage.class).loadAnimations(Arrays.stream(animationContainers).toList());
        } catch (IOException e) {
            log.fatal("Error loading animations", e);
            throw new RuntimeException(e);
        }

        if (settingsService.getSettings().getGraphics().getCursorSprite() != null) {
            Texture texture = Context.getService(SpriteStorage.class).getSprite(settingsService.getSettings().getGraphics().getCursorSprite()).texture();
            Context.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getMouse().getCursor().setTexture(texture);
        }

        Texture icon = Context.getService(SpriteStorage.class).getSprite("window_icon").texture();
        Context.getService(Render.class).setIcon(icon);

        guiPanelStorage.init();
        mapObjectFactory.init();
        armorCreatorsStorage.init();
        gunCreatorsStorage.init();
        bulletCreatorsStorage.init();
        armorSpecificationStorage.init();
        gunSpecificationStorage.init();
        bulletSpecificationStorage.init();
        bulletBehaviorsCreator.init();

        String defaultMap = devSettingsService.getDevSettings().getDefaultMap();
        if (defaultMap != null) {
            ServerLoader.mapPath = "maps/" + defaultMap;
        }
        Context.getService(GuiService.class).setMaskRendering(devSettingsService.getDevSettings().isMaskRendering());

        locationManager.setActiveLocation(new MenuLocation(!settingsService.isLoadSuccess()));
    }

    @Override
    public Render.Settings getRenderSettings() {
        Settings.Graphics graphics = settingsService.getSettings().getGraphics();
        return new Render.Settings(graphics.getWidthScreen(), graphics.getHeightScreen(), graphics.isFullScreen(),
                graphics.getFpsLimit(), graphics.getVSyncDivider(), WINDOW_NAME);
    }

    @Override
    public void render() {
        GameInterface.super.render();
        PlayerTank playerTank = Context.getService(ClientData.class).player;
        if (playerTank != null) {

            Context.getService(LightSystemService.class).light(
                    (int) playerTank.getX(),
                    (int) playerTank.getY(),
                    //(int) playerTank.getDirection(),
                    400, 0);
        }
    }
}