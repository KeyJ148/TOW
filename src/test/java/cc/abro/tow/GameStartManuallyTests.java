package cc.abro.tow;

import cc.abro.orchengine.Loader;
import cc.abro.orchengine.Manager;
import cc.abro.tow.client.NetGameRead;
import cc.abro.tow.client.menu.panels.controllers.connectbyip.ClickConnectController;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickCreateController;
import cc.abro.tow.client.menu.panels.events.connectbyip.ClickConnectGuiEvent;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import cc.abro.tow.server.NetServerRead;
import cc.abro.tow.server.Server;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class GameStartManuallyTests extends GameStartTests{

    @Test
    public void gameStartAndCreateServerTestManually() {
        Runnable afterStart = () -> new ClickCreateController().processEvent(new ClickCreateGuiEvent("25566", 1));
        Manager.addService(new GameAfterStartService(afterStart));
        Loader.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    @Test
    public void gameStartAndCreateServer2PlayerTestManually() {
        Runnable afterStart = () -> new ClickCreateController().processEvent(new ClickCreateGuiEvent("25566", 2));
        Manager.addService(new GameAfterStartService(afterStart));
        Loader.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    @Test
    public void gameStartAndConnectToLocalhostTestManually() {
        Runnable afterStart = () -> new ClickConnectController().processEvent(new ClickConnectGuiEvent("127.0.0.1", "25566"));
        Manager.addService(new GameAfterStartService(afterStart));
        Loader.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

}
