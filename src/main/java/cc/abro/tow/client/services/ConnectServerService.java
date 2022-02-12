package cc.abro.tow.client.services;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.net.client.ConnectException;
import cc.abro.orchengine.net.client.Connector;

import java.net.InetAddress;

@GameService
public class ConnectServerService {

    private boolean wasConnect = false;//TODO AtomiсBoolean (так же и для создания сервера) Теперь же нет потоков???

    public void connect(InetAddress ip, int port) {
        if (wasConnect) {
            return;
        }
        wasConnect = true;

        if (port < 1024 || port > 65535) {
            throw new WrongPortException();
        }

        try {
            Context.createBean(Connector.class).connect(ip.getHostAddress(), port);
        } catch (ConnectException e) {
            e.printStackTrace();
            wasConnect = false;
            throw e;
        }
    }

    public class WrongPortException extends RuntimeException {}
}
