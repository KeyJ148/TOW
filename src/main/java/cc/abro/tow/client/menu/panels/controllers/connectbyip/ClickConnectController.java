package cc.abro.tow.client.menu.panels.controllers.connectbyip;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.net.client.ConnectException;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.orchengine.services.BlockingPanelService;
import cc.abro.tow.client.menu.panels.controllers.MenuClickController;
import cc.abro.tow.client.menu.panels.events.connectbyip.ClickConnectGuiEvent;
import cc.abro.tow.client.menu.panels.gui.CreateGameMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.LabelBlockingGuiPanel;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class ClickConnectController extends MenuClickController<ClickConnectGuiEvent> {

    private final static String DEFAULT_IP = "127.0.0.1";

    private boolean wasConnect = false;

    @Override
    public Class<ClickConnectGuiEvent> getProcessedEventClass() {
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
            createButtonBlockingPanel(Error.WRONG_IP.getText(), BLOCKING_BUTTON_ELEMENT_WIDTH, BLOCKING_BUTTON_ELEMENT_HEIGHT);
            wasConnect = false;
        }

        new Thread(() -> {
            EventableGuiElement<LabelBlockingGuiPanel> guiElement = createLabelBlockingPanel("Connecting...", CONNECTING_ELEMENT_WIDTH, CONNECTING_ELEMENT_HEIGHT);
            EventableGuiElement<CreateGameMenuGuiPanel> gg = null;
            BlockingPanelService.GuiPanelBlock block = Manager.getService(BlockingPanelService.class).createGuiPanelBlock(gg.getComponent());

            try {
                Manager.createBean(Connector.class).connect(ip, Integer.parseInt(event.getPort()));
                //TODO создать наследника EventableGuiElement в котором релизовать метод destroyAndFocused
                block.unblock();
                //guiElement.getComponent().focusComponents();
                createLabelBlockingPanel("Waiting for players...", WAITING_ELEMENT_WIDTH, CONNECTING_ELEMENT_HEIGHT);
            } catch (ConnectException e) {
                //На самом деле этот setFocusable бесполезен, т.к. следующая BlockingPanel зблокирует эти же компоненты
                guiElement.getComponent().focusComponents();
                createButtonBlockingPanel(Error.SERVER_NOT_FOUND.getText(), BLOCKING_BUTTON_ELEMENT_WIDTH, BLOCKING_BUTTON_ELEMENT_HEIGHT);
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
