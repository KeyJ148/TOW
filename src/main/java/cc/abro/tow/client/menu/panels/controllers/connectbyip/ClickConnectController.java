package cc.abro.tow.client.menu.panels.controllers.connectbyip;

import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.client.menu.panels.events.connectbyip.ClickConnectGuiEvent;

public class ClickConnectController extends GuiElementController<ClickConnectGuiEvent> {

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
        new Connector().connect(ip, Integer.parseInt(event.getPort()));
    }
}
