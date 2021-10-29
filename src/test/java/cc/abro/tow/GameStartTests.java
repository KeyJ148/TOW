package cc.abro.tow;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Manager;
import cc.abro.orchengine.OrchEngine;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.orchengine.profiles.Profile;
import cc.abro.orchengine.profiles.Profiles;
import cc.abro.tow.client.Game;
import cc.abro.tow.client.NetGameRead;
import cc.abro.tow.client.menu.StartServerListener;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickCreateController;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import cc.abro.tow.server.NetServerRead;
import cc.abro.tow.server.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Set;
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
    public void gameStartAndCreateServerTest(){
        Runnable gameAfterStart = () -> connect("25566", 1);
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
        Global.engine.stop();
        waitToLog("Shutting down logger");
        Assertions.assertFalse(hasException.get(), "Has exception in game main thread");
    }

    public void connect(String port, int peopleMax){
        ClickCreateController controller = new ClickCreateController();
        controller.init(new EventableGuiPanelElement<>(new EventableGuiPanel(){}, Set.of()));
        controller.processEvent(new ClickCreateGuiEvent(port, peopleMax));
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

        public GameProxyService(GuiPanelStorage guiPanelStorage, GameAfterStartService gameAfterStartService) {
            super(guiPanelStorage);
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

    public static class StartServerListenerImpl implements StartServerListener {

        private final int port;

        public StartServerListenerImpl(int port) {
            this.port = port;
        }

        @Override
        public void serverStart() {
            Manager.createBean(Connector.class).connect("127.0.0.1", port);
        }
    }
}
