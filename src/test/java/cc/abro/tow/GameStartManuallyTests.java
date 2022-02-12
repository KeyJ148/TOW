package cc.abro.tow;

import cc.abro.orchengine.OrchEngine;
import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.client.NetGameRead;
import cc.abro.tow.client.services.CreateServerService;
import cc.abro.tow.server.NetServerRead;
import cc.abro.tow.server.Server;
import cc.abro.tow.server.ServerLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicReference;

import static cc.abro.tow.TestUtils.*;
import static cc.abro.tow.logs.LogUtils.waitToLog;
import static cc.abro.tow.logs.LogUtils.waitToLogRegex;
import static cc.abro.tow.services.ServiceUtils.Profiles.TEST_NOT_SHUTDOWN;

public class GameStartManuallyTests {

    private static final String[] ACTIVE_PROFILES = {TEST_NOT_SHUTDOWN};

    @Test
    @Timeout(value = 10)
    public void gameStartAndCreateServerTestManually() {
        AtomicReference<Boolean> hasException = new AtomicReference<>(false);

        new Thread(() -> {
            try {
                Runnable gameAfterStart = () -> Context.getService(CreateServerService.class).createServer("25566", 1);
                Context.addService(new GameAfterStartService(gameAfterStart));
                GameStart.main(ACTIVE_PROFILES);
            } catch (Exception e) {
                e.printStackTrace();
                hasException.set(true);
            }
        }).start();

        waitToLogRegex("(Load map)(.*)(completed)");
        Context.getService(Engine.class).stop();
        waitToLog("Shutting down all services complete");
        Assertions.assertFalse(hasException.get(), "Has exception in game main thread");
    }

    @Test
    public void gameStartAndCreateServerManually() {
        Context.addService(createStartServerAfterStartGameService(1));
        OrchEngine.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    @Test
    public void gameStartAndCreateServer2PlayerManually() {
        Context.addService(createStartServerAfterStartGameService(2));
        OrchEngine.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    @Test
    public void gameStartAndConnectToLocalhostManually() {
        Context.addService(createConnectAfterStartGameService());
        OrchEngine.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    @Test
    public void gameStartAndConnectToLocalhostManually2() {
        Context.addService(createConnectAfterStartGameService());
        OrchEngine.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    private GameAfterStartService createStartServerAfterStartGameService(int peopleMax){
        return createStartServerAfterStartGameService(DEFAULT_PORT, peopleMax);
    }

    private GameAfterStartService createStartServerAfterStartGameService(int port, int peopleMax){
        ServerLoader.startServerListener = new StartServerListenerImpl(port);
        return new GameAfterStartService(() -> new ServerLoader(port, peopleMax, false));
    }

    private GameAfterStartService createConnectAfterStartGameService(){
        return createConnectAfterStartGameService(DEFAULT_IP, DEFAULT_PORT);
    }

    private GameAfterStartService createConnectAfterStartGameService(String ip, int port){
        return new GameAfterStartService(() -> Context.createBean(Connector.class).connect(ip, port));
    }

}
