package cc.abro.tow;

import cc.abro.orchengine.context.TestService;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.orchengine.resources.locales.LocalizationService;
import cc.abro.tow.client.Game;
import cc.abro.tow.client.map.factory.MapObjectFactory;
import cc.abro.tow.client.settings.DevSettingsService;
import cc.abro.tow.client.settings.SettingsService;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.armor.ArmorSpecificationStorage;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.BulletModifierCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.BulletModifierSpecificationStorage;
import cc.abro.tow.client.tanks.equipment.gun.GunCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.gun.GunSpecificationStorage;

public class TestUtils {

    public static class Profiles {
        public static final String TEST_DISABLE_RENDER = "TestDisableRender";
    }

    public static final String DEFAULT_IP = "127.0.0.1";
    public static final int DEFAULT_PORT = 25566;

    /*
     * Execute Runnable after the game started
     */
    public static class GameAfterStartService {

        private final Runnable runAfterStart;

        public GameAfterStartService(Runnable runAfterStart) {
            this.runAfterStart = runAfterStart;
        }

        public void run() {
            runAfterStart.run();
        }
    }

    @TestService
    public static class GameProxyService extends Game {

        private boolean started = false;
        private final GameAfterStartService gameAfterStartService;

        public GameProxyService(GuiPanelStorage guiPanelStorage,
                                LocationManager locationManager,
                                AudioService audioService,
                                SettingsService settingsService,
                                DevSettingsService devSettingsService,
                                LocalizationService localizationService,
                                MapObjectFactory mapObjectFactory,
                                ArmorCreatorsStorage armorCreatorsStorage,
                                GunCreatorsStorage gunCreatorsStorage,
                                BulletModifierCreatorsStorage bulletModifierCreatorsStorage,
                                ArmorSpecificationStorage armorSpecificationStorage,
                                GunSpecificationStorage gunSpecificationStorage,
                                BulletModifierSpecificationStorage bulletModifierSpecificationStorage,
                                BulletCreatorsStorage bulletCreatorsStorage,
                                GameAfterStartService gameAfterStartService) {
            super(guiPanelStorage, locationManager, audioService, settingsService, devSettingsService,
                    localizationService, mapObjectFactory, armorCreatorsStorage, gunCreatorsStorage,
                    bulletModifierCreatorsStorage, armorSpecificationStorage, gunSpecificationStorage,
                    bulletModifierSpecificationStorage, bulletCreatorsStorage);
            this.gameAfterStartService = gameAfterStartService;
        }

        @Override
        public void update(long delta) {
            super.update(delta);
            if (!started) {
                started = true;
                gameAfterStartService.run();
            }
        }
    }

    public static class StartServerListenerImpl implements Runnable {

        private final int port;

        public StartServerListenerImpl(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            new Connector().connect("127.0.0.1", port);
        }
    }
}
