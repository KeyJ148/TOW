package cc.abro.tow.client.menu.panels.controllers.creategame;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.client.menu.StartServerListener;
import cc.abro.tow.client.menu.panels.controllers.MenuClickController;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import cc.abro.tow.server.ServerLoader;

import static cc.abro.tow.client.menu.InterfaceStyles.ERROR_ELEMENT_HEIGHT;
import static cc.abro.tow.client.menu.InterfaceStyles.ERROR_ELEMENT_WIDTH;

public class ClickCreateController extends MenuClickController<ClickCreateGuiEvent> implements StartServerListener {

    private int port;
    private boolean serverLaunching = false;

    @Override
    protected Class<ClickCreateGuiEvent> getProcessedEventClass() {
        return ClickCreateGuiEvent.class;
    }

    @Override
    public void processEvent(ClickCreateGuiEvent event) {
        if (serverLaunching) {
            createBlockingPanelWithButton(Error.SERVER_LAUNCHING.getText(), ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);
        } else {
            try {
                port = Integer.parseInt(event.getPort());
                if (port < 1024 || port > 65535)
                    throw new NumberFormatException();
                ServerLoader.startServerListener = this;
                serverLaunching = true;
                new ServerLoader(port, event.getPeopleMax(), false);
            } catch (NumberFormatException e) {
                createBlockingPanelWithButton(Error.WRONG_PORT.getText(), ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);
            } catch (RuntimeException e) {
                createBlockingPanelWithButton(Error.UNKNOWN.getText(), ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);
            }
        }
    }

    @Override
    public void serverStart() {
        Manager.createBean(Connector.class).connect("127.0.0.1", port);
    }


    public enum Error {
        WRONG_PORT("ERROR: Port must be an integer 1024-65535"),
        SERVER_LAUNCHING("ERROR: Server is launching"),
        UNKNOWN("ERROR: Something went wrong");

        private final String text;

        Error(String text) {
            this.text = text;
        }

        public String getText(){
            return text;
        }
    }
}
