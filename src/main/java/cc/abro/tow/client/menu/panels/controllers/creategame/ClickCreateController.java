package cc.abro.tow.client.menu.panels.controllers.creategame;

import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.client.menu.StartServerListener;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import cc.abro.tow.client.menu.panels.gui.PrintErrorGuiPanel;
import cc.abro.tow.server.ServerLoader;

public class ClickCreateController extends GuiElementController<ClickCreateGuiEvent> implements StartServerListener {

    private int port;
    private boolean serverLaunching = false;

    @Override
    public void processEvent(ClickCreateGuiEvent event) {
        if (serverLaunching) {
            new PrintErrorGuiPanel(Error.SERVER_LAUNCHING.getText(), getGuiElement().getComponent());
        } else {
            try {
                port = Integer.parseInt(event.getPort());
                if (port < 1024 || port > 65535)
                    throw new NumberFormatException();
                ServerLoader.startServerListener = this;
                serverLaunching = true;
                new ServerLoader(port, event.getPeopleMax(), false);
            } catch (NumberFormatException e) {
                new PrintErrorGuiPanel(Error.WRONG_PORT.getText(), getGuiElement().getComponent());
            } catch (RuntimeException e) {
                new PrintErrorGuiPanel(Error.UNKNOWN.getText(), getGuiElement().getComponent());
            }
        }
    }

    @Override
    public void serverStart() {
        new Connector().connect("127.0.0.1", port);
    }

    public enum Error {
        WRONG_PORT("ERROR: Port must be integer 1024-65535"),
        UNKNOWN("ERROR: Server is launching"),
        SERVER_LAUNCHING("ERROR: Something went wrong");

        private final String text;

        Error(String text) {
            this.text = text;
        }

        public String getText(){
            return text;
        }
    }
}
