package cc.abro.tow;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.OrchEngine;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.orchengine.profiles.Profile;
import cc.abro.orchengine.profiles.Profiles;
import cc.abro.tow.client.Game;
import cc.abro.tow.client.NetGameRead;
import cc.abro.tow.client.services.CreateServerService;
import cc.abro.tow.server.NetServerRead;
import cc.abro.tow.server.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicReference;

import static cc.abro.tow.LogUtils.waitToLog;
import static cc.abro.tow.LogUtils.waitToLogRegex;

public class GameStartTests {

    @BeforeAll
    public static void setUpAll() {
        Profiles.initProfile(Profile.TESTS);
    }

    @Test
    @Timeout(value = 10)
    public void gameStartAndCreateServerTest() {
        Runnable gameAfterStart = () -> Manager.getService(CreateServerService.class).createServer("25566", 1);
        Manager.addService(new GameAfterStartService(gameAfterStart));
        AtomicReference<Boolean> hasException = new AtomicReference<>(false);
        new Thread(() -> {
            try {
                OrchEngine.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
            } catch (Exception e) {
                e.printStackTrace();
                hasException.set(true);
            }
        }).start();
        waitToLogRegex("(Load map)(.*)(completed)");
        Manager.getService(Engine.class).stop();
        waitToLog("Shutting down logger");
        Assertions.assertFalse(hasException.get(), "Has exception in game main thread");
    }

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

    public static class GameProxyService extends Game {

        private boolean started = false;
        private final GameAfterStartService gameAfterStartService;

        public GameProxyService(GuiPanelStorage guiPanelStorage, LocationManager locationManager,
                                GameAfterStartService gameAfterStartService) {
            super(guiPanelStorage, locationManager);
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
            Manager.createBean(Connector.class).connect("127.0.0.1", port);
        }
    }
}
