package cc.abro.tow;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.client.services.CreateServerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicReference;

import static cc.abro.tow.TestUtils.*;
import static cc.abro.tow.logs.LogUtils.waitToLog;
import static cc.abro.tow.logs.LogUtils.waitToLogRegex;
import static cc.abro.tow.services.ServiceUtils.Profiles.TEST_DISABLE_RENDER;

public class GameStartTests {

    private static final String[] ACTIVE_PROFILES = {TEST_DISABLE_RENDER};

    @Test
    @Timeout(value = 10)
    public void gameStartAndCreateServerTest() {
        AtomicReference<Boolean> hasException = new AtomicReference<>(false);

        ThreadGroup testThreadGroup = new ThreadGroup(Thread.currentThread().getThreadGroup(), "test");
        new Thread(testThreadGroup, () -> {
            try {
                Runnable afterStart = () -> Context.getService(CreateServerService.class).createServer("25566", 1);
                Context.addService(new GameAfterStartService(afterStart));
                GameStart.main(ACTIVE_PROFILES);
            } catch (Exception e) {
                e.printStackTrace();
                hasException.set(true);
            }
        }).start();

        waitToLogRegex("(Load map)(.*)(completed)");
        Context.getService(Engine.class, testThreadGroup).stop();
        waitToLog("Shutting down all services complete");
        Assertions.assertFalse(hasException.get(), "Has exception in game main thread");
    }

    @Test
    @Timeout(value = 10)
    public void gameStartAndCreateServer2PlayerTest() {
        AtomicReference<Boolean> hasException = new AtomicReference<>(false);

        ThreadGroup serverThreadGroup = new ThreadGroup(Thread.currentThread().getThreadGroup(), "test-server");
        new Thread(serverThreadGroup, () -> {
            try {
                Runnable afterStart = () -> Context.getService(CreateServerService.class).createServer("25566", 2);
                Context.addService(new GameAfterStartService(afterStart));
                GameStart.main(ACTIVE_PROFILES);
            } catch (Exception e) {
                e.printStackTrace();
                hasException.set(true);
            }
        }).start();

        waitToLogRegex("Server started");

        ThreadGroup clientThreadGroup = new ThreadGroup(Thread.currentThread().getThreadGroup(), "test-client");
        new Thread(clientThreadGroup, () -> {
            try {
                Runnable afterStart = () -> Context.createBean(Connector.class).connect(DEFAULT_IP, DEFAULT_PORT);
                Context.addService(new GameAfterStartService(afterStart));
                GameStart.main(ACTIVE_PROFILES);
            } catch (Exception e) {
                e.printStackTrace();
                hasException.set(true);
            }
        }).start();

        waitToLog(log -> log.matches("(Load map)(.*)(completed)"), 2);
        Context.getService(Engine.class, serverThreadGroup).stop();
        Context.getService(Engine.class, clientThreadGroup).stop();
        waitToLog("Shutting down all services complete");
        Assertions.assertFalse(hasException.get(), "Has exception in game main thread");
    }
}
