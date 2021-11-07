package cc.abro.tow.client.menu.panels.controllers.creategame;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.orchengine.net.server.GameServer;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.menu.StartServerListener;
import cc.abro.tow.client.menu.panels.controllers.MenuClickController;
import cc.abro.tow.client.menu.panels.controllers.main.CloseChildPanelController;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import cc.abro.tow.client.menu.panels.gui.BlockingGuiPanel;
import cc.abro.tow.server.ServerLoader;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

@Log4j2
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
            createBlockingPanelWithButton(Error.SERVER_LAUNCHING.getText(), BLOCKING_BUTTON_ELEMENT_WIDTH, BLOCKING_BUTTON_ELEMENT_HEIGHT);
        } else {
            try {
                port = Integer.parseInt(event.getPort());
                if (port < 1024 || port > 65535)
                    throw new NumberFormatException();
                ServerLoader.startServerListener = this;
                serverLaunching = true;

                new ServerLoader(port, event.getPeopleMax(), false);
                //TODO возможно нужно избавиться от дублирования
                BlockingGuiPanel guiPanel = new BlockingGuiPanel("Connected players: ", CONNECTED_PLAYERS_ELEMENT_WIDTH, CONNECTING_ELEMENT_HEIGHT,
                        getGuiElement().getComponent());
                EventableGuiPanelElement<BlockingGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                        Set.of(new CloseChildPanelController())) {
                    @Override
                    public void updateComponent(long delta) {
                        super.updateComponent(delta);
                        getComponent().getLabel().getTextState().setText("Connected players: " + GameServer.peopleNow + "/" + GameServer.peopleMax);
                    }
                };
                Vector2<Double> position = getGuiElement().getPosition();
                Manager.getService(GuiElementService.class).addGuiElementToLocationShiftedToCenter(guiElement,
                        position.x.intValue(), position.y.intValue(),
                        getGuiElement().getGameObject().getLocation());
            } catch (NumberFormatException e) {
                createBlockingPanelWithButton(Error.WRONG_PORT.getText(), BLOCKING_BUTTON_ELEMENT_WIDTH, BLOCKING_BUTTON_ELEMENT_HEIGHT);
            } catch (RuntimeException e) {
                log.error(e);
                e.printStackTrace();//TODO need remove, but log.error not working
                createBlockingPanelWithButton(Error.UNKNOWN.getText(), BLOCKING_BUTTON_ELEMENT_WIDTH, BLOCKING_BUTTON_ELEMENT_HEIGHT);
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
