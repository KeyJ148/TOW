package cc.abro.orchengine.net.client;

import cc.abro.orchengine.exceptions.EngineException;

public class ConnectException extends EngineException {
    public ConnectException() {
    }

    public ConnectException(String message) {
        super(message);
    }

    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectException(Throwable cause) {
        super(cause);
    }
}
