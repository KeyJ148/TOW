package cc.abro.tow.client.services;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.net.client.ConnectException;
import cc.abro.orchengine.net.client.Connector;
import lombok.extern.log4j.Log4j2;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;

@GameService
@Log4j2
public class ConnectServerService {

    private final AtomicBoolean wasConnect = new AtomicBoolean(false);

    public void connect(InetAddress ip, int port) {
        if (wasConnect.compareAndSet(false, true)) {
            return;
        }

        if (port < 1024 || port > 65535) {
            throw new WrongPortException();
        }

        try {
            new Connector().connect(ip.getHostAddress(), port);
        } catch (ConnectException e) {
            wasConnect.set(false);
            throw e;
        }
    }

    public static class WrongPortException extends RuntimeException {}
}
