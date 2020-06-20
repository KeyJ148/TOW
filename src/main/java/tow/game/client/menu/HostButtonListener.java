package tow.game.client.menu;

import tow.engine.net.client.Connector;
import tow.game.client.ClientData;
import tow.game.server.ServerLoader;

import java.util.List;
import java.util.function.Consumer;

public class HostButtonListener extends MenuLocation implements StartServerListener{

    private int port;
    private Consumer<Error> errorConsumer;
    private boolean serverLaunching = false;

    public HostButtonListener(Consumer<Error> errorConsumer) {
        this.errorConsumer = errorConsumer;
    }

    public void host(String portString) {
        if(serverLaunching) {
            errorConsumer.accept(Error.SERVER_LAUNCHING);
        }
        else {
            try {
                port = Integer.parseInt(portString);
                ClientData.name = "Vaster";
                ServerLoader.startServerListener = this;
                serverLaunching = true;
                new ServerLoader(port, 1, false);
            } catch (NumberFormatException e) {
                errorConsumer.accept(Error.WRONG_LETTERS_IN_PORT);
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
        WRONG_LETTERS_IN_PORT,
        UNKNOWN,
        SERVER_LAUNCHING
    }
}
