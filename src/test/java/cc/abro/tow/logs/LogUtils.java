package cc.abro.tow.logs;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

import java.util.concurrent.*;
import java.util.function.Function;

public class LogUtils {

    private static final long APPENDER_WAIT_TIMEOUT_MS = 5000;

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
        waitToLog(messageFoundChecker, 1);
    }

    @SneakyThrows
    public static void waitToLog(Function<String, Boolean> messageFoundChecker, int countRepeats) {
        BlockingQueue<String> log = getLog();
        CountDownLatch latch = new CountDownLatch(countRepeats);
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

    @SneakyThrows
    public static BlockingQueue<String> getLog() {
        long appenderWaitStartTime = System.currentTimeMillis();

        Appender appender;
        while ((appender = getAppender(TestAppender.NAME)) == null) {
            if (System.currentTimeMillis() - appenderWaitStartTime > APPENDER_WAIT_TIMEOUT_MS) {
                throw new RuntimeException("TestAppender not found");
            }
            Thread.sleep(100);
        }
        return ((TestAppender) appender).getMessages();
    }

    private static Appender getAppender(String appenderName) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        return config.getAppenders().get(appenderName);
    }
}
