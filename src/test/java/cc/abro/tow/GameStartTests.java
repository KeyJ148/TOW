package cc.abro.tow;

import cc.abro.orchengine.Loader;
import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.tow.client.Game;
import cc.abro.tow.client.NetGameRead;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickCreateController;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import cc.abro.tow.server.NetServerRead;
import cc.abro.tow.server.Server;
import org.junit.jupiter.api.Test;

public class GameStartTests {

    @Test
    public void gameStartAndCreateServerTest() throws InterruptedException {
        Runnable afterStart = () -> new ClickCreateController().processEvent(new ClickCreateGuiEvent("25566", 1));
        Manager.addService(new GameAfterStartService(afterStart));
        new Thread(() -> Loader.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class)).start();
        Thread.sleep(5000);
        Manager.getService(Engine.class).stop();
    }

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
}
