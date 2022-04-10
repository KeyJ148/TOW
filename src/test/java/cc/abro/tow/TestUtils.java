package cc.abro.tow;

import cc.abro.orchengine.audio.AudioPlayer;
import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.TestService;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.Game;
import cc.abro.tow.client.settings.SettingsService;

public class TestUtils {
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
                                ClientData clientData,
                                AudioPlayer audioPlayer,
                                SettingsService settingsService,
                                GameAfterStartService gameAfterStartService) {
            super(guiPanelStorage, locationManager, clientData, audioPlayer, settingsService);
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
            Context.createBean(Connector.class).connect("127.0.0.1", port);
        }
    }
}
