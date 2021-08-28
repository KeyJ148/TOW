package cc.abro.tow.client.menu.panels.controllers.creategame;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.menu.StartServerListener;
import cc.abro.tow.client.menu.panels.controllers.main.CloseChildPanelController;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import cc.abro.tow.client.menu.panels.gui.PrintErrorGuiPanel;
import cc.abro.tow.server.ServerLoader;

import java.util.Set;

public class ClickCreateController extends GuiElementController<ClickCreateGuiEvent> implements StartServerListener {

    private int port;
    private boolean serverLaunching = false;

    @Override
    protected Class<ClickCreateGuiEvent> getProcessedEventClass() {
        return ClickCreateGuiEvent.class;
    }

    @Override
    public void processEvent(ClickCreateGuiEvent event) {
        if (serverLaunching) {
            createErrorPanel(Error.SERVER_LAUNCHING);
        } else {
            try {
                port = Integer.parseInt(event.getPort());
                if (port < 1024 || port > 65535)
                    throw new NumberFormatException();
                ServerLoader.startServerListener = this;
                serverLaunching = true;
                new ServerLoader(port, event.getPeopleMax(), false);
            } catch (NumberFormatException e) {
                createErrorPanel(Error.WRONG_PORT);
            } catch (RuntimeException e) {
                createErrorPanel(Error.UNKNOWN);
            }
        }
    }

    @Override
    public void serverStart() {
        Manager.createBean(Connector.class).connect("127.0.0.1", port);
    }

    //TODO вынести в другое место (общий сервис?) (передавать error.getText())
    private void createErrorPanel(Error error) {
        PrintErrorGuiPanel guiPanel = new PrintErrorGuiPanel(error.getText(),
                getGuiElement().getComponent());
        EventableGuiPanelElement<EventableGuiPanel> guiElement = new EventableGuiPanelElement<>(
                guiPanel, Set.of(new CloseChildPanelController()));
        Vector2<Double> position = getGuiElement().getPosition();
        Manager.getService(GuiElementService.class).addGuiElementToLocationShiftedToCenter(guiElement,
                position.x.intValue(), position.y.intValue(),
                getGuiElement().getGameObject().getComponent(Position.class).location);
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
