package cc.abro.orchengine.init;

import cc.abro.orchengine.OrchEngine;
import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.util.LogUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;

/**
 * Несмотря на аннотацию {@link EngineService} данный сервис нельзя переопределить другим сервисом.
 * Т.к. этот сервис инициализируется до сканирования пакетов на сервисы.
 * При этом сервис всё ещё находится в {@link Context} в ThreadContext конкретной {@link ThreadGroup}.
 * При параллельном запуске нескольких экземпляров {@link OrchEngine} у каждого из них будет свой {@link Finalizer}
 */
@Log4j2
@EngineService
public class Finalizer {

    void registryShutdownCallback() {
        Thread shutdownCallbackThread = new Thread(this::stopServicesAndCloseResources);
        shutdownCallbackThread.setName("ShutdownHook");
        Runtime.getRuntime().addShutdownHook(shutdownCallbackThread);
    }

    public void stopServicesAndCloseResources() {
        try {
            log.debug("Shutting down all services...");
            Context.stop();
            log.debug("Shutting down all services complete");
        } catch (Exception e) {
            LogUtils.logFatalException(log, "Stopping services ended with an error: ", e);
        }
        log.debug("Shutting down logger");
        LogManager.shutdown();
    }
}
