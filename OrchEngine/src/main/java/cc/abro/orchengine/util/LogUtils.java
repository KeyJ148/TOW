package cc.abro.orchengine.util;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.Logger;

@UtilityClass
public class LogUtils {

    public void logFatalException(Logger log, String text, Exception e) {
        e.printStackTrace();
        try {
            log.fatal(text, e);
        } catch (Exception logException) {
            logException.printStackTrace();
        }
    }
}
