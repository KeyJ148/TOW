package cc.abro.tow.client.services;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.server.ServerLoader;
import lombok.extern.log4j.Log4j2;

@Log4j2
@GameService
public class CreateServerService {

    private int port;
    private boolean serverLaunching = false;

    public void createServer(String inputPort, int maxPeople) {
        if (serverLaunching) {
            throw new ServerIsLaunchingExeption();
        }

        port = Integer.parseInt(inputPort);
        if (port < 1024 || port > 65535) {
            throw new WrongPortException();
        }

        try {
            ServerLoader.startServerListener = () -> new Connector().connect("127.0.0.1", port);
            serverLaunching = true;
            new ServerLoader(port, maxPeople, false);
        } catch (RuntimeException e) {
            log.error("Error while server starting: ", e);
            throw e;
        }
    }

    public static class ServerIsLaunchingExeption extends RuntimeException {}
    public static class WrongPortException extends RuntimeException {}
}
