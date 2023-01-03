package cc.abro.orchengine.exceptions;

public class EngineException extends RuntimeException {

    public EngineException() {
    }

    public EngineException(String message) {
        super(message);
    }

    public EngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public EngineException(Throwable cause) {
        super(cause);
    }
}
