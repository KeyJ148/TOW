package cc.abro.tow.client.menu;

import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.server.ServerLoader;

import java.util.function.Consumer;

public class HostingListener implements StartServerListener {

    private int port;
    private Consumer<Error> errorConsumer;
    private boolean serverLaunching = false;

    public HostingListener(Consumer<Error> errorConsumer) {
        this.errorConsumer = errorConsumer;
    }

    public void host(String portString, int peopleMax) {
        if (serverLaunching) {
            errorConsumer.accept(Error.SERVER_LAUNCHING);
        } else {
            try {
                port = Integer.parseInt(portString);
                if (port < 1024 || port > 65535)
                    throw new NumberFormatException();
                ServerLoader.startServerListener = this;
                serverLaunching = true;
                new ServerLoader(port, peopleMax, false);
            } catch (NumberFormatException e) {
                errorConsumer.accept(Error.WRONG_PORT);
            } catch (RuntimeException e) {
                errorConsumer.accept(Error.UNKNOWN);
            }
        }
    }

    @Override
    public void serverStart() {
        new Connector().connect("127.0.0.1", port);
    }

    public enum Error {
        WRONG_PORT {
            @Override
            public String getText() {
                return "ERROR: Port must be integer 1024-65535";
            }
        },
        UNKNOWN {
            @Override
            public String getText() {
                return "ERROR: Server is launching";
            }
        },
        SERVER_LAUNCHING {
            @Override
            public String getText() {
                return "ERROR: Something went wrong";
            }
        };

        public abstract String getText();
    }
}
