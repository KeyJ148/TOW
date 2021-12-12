package cc.abro.tow.client.services;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.server.ServerLoader;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CreateServerService {

    private int port;
    private boolean serverLaunching = false;

    public void createServer(String inputPort, int maxPeople) {
        if (serverLaunching) {
            throw new ServerIsLaunchingExeption();
        } else {
            try {
                port = Integer.parseInt(inputPort);
                if (port < 1024 || port > 65535) {
                    throw new WrongPortException();
                }
                ServerLoader.startServerListener = () -> Manager.createBean(Connector.class).connect("127.0.0.1", port);
                serverLaunching = true;

                new ServerLoader(port, maxPeople, false);
            } catch (RuntimeException e) {
                log.error(e);
                e.printStackTrace();//TODO need remove, but log.error not working
                throw e;
            }
        }
    }

    public class ServerIsLaunchingExeption extends RuntimeException {}
    public class WrongPortException extends RuntimeException {}
}
