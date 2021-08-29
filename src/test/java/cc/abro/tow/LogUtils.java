package cc.abro.tow;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

import java.util.concurrent.*;
import java.util.function.Function;

public class LogUtils {
    @SneakyThrows
    public static void waitToLog(String logMessage) {
        waitToLog(logMessage::equals);
    }

    @SneakyThrows
    public static void waitToLogRegex(String logMessageRegex) {
        waitToLog((log) -> log.matches(logMessageRegex));
    }

    @SneakyThrows
    public static void waitToLog(Function<String, Boolean> messageFoundChecker) {
        BlockingQueue<String> log = getLog();
        CountDownLatch latch = new CountDownLatch(1);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            String logMessage;
            while ((logMessage = log.poll()) != null) {
                if (messageFoundChecker.apply(logMessage)) {
                    latch.countDown();
                }
            }
        }, 0, 50, TimeUnit.MILLISECONDS);
        latch.await();
        executor.shutdownNow();
    }

    public static BlockingQueue<String> getLog() {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        return ((TestAppender) config.getAppenders().get(TestAppender.NAME)).getMessages();
    }
}
