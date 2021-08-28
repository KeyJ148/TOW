package cc.abro.tow.client.menu.panels.controllers.connectbyip;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.orchengine.net.client.ConnectException;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.client.menu.panels.controllers.MenuClickController;
import cc.abro.tow.client.menu.panels.events.connectbyip.ClickConnectGuiEvent;
import cc.abro.tow.client.menu.panels.gui.BlockingGuiPanel;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class ClickConnectController extends MenuClickController<ClickConnectGuiEvent> {

    private final static String DEFAULT_IP = "127.0.0.1";

    private boolean wasConnect = false;

    @Override
    protected Class<ClickConnectGuiEvent> getProcessedEventClass() {
        return ClickConnectGuiEvent.class;
    }

    @Override
    public void processEvent(ClickConnectGuiEvent event) {
        if (wasConnect){
            return;
        }
        wasConnect = true;

        String ip = event.getIp() != null ? event.getIp() : DEFAULT_IP;
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            createBlockingPanelWithButton(Error.WRONG_IP.getText(), ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);
            wasConnect = false;
        }

        new Thread(() -> {
            EventableGuiPanelElement<BlockingGuiPanel> guiElement = createBlockingPanel("Connecting...", CONNECTING_ELEMENT_WIDTH, CONNECTING_ELEMENT_HEIGHT);
            try {
                Manager.createBean(Connector.class).connect(ip, Integer.parseInt(event.getPort()));
                //TODO создать наследника EventableGuiPanelElement в котором релизовать метод destroyAndFocused
                guiElement.getComponent().getUnfocusedComponents().iterator().forEachRemaining(c -> c.setFocusable(true));
            } catch (ConnectException e) {
                //На самом деле этот setFocusable бесполезен, т.к. следующая BlockingPanel зблокирует эти же компоненты
                guiElement.getComponent().getUnfocusedComponents().iterator().forEachRemaining(c -> c.setFocusable(true));
                createBlockingPanelWithButton(Error.SERVER_NOT_FOUND.getText(), ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);
                wasConnect = false;
            } finally {
                guiElement.destroy();
            }
        }).start();
    }

    public enum Error {
        WRONG_IP("ERROR: IP address written incorrectly"),
        SERVER_NOT_FOUND("ERROR: Server not found"),
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
