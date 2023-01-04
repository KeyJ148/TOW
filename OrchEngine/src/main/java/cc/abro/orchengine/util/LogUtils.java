package cc.abro.orchengine.util;


import org.apache.logging.log4j.Logger;

public class LogUtils {

    public static void logFatalException(Logger log, String text, Exception e) {
        e.printStackTrace();
        try {
            log.fatal(text, e);
        } catch (Exception logException) {
            logException.printStackTrace();
        }
    }
}
