package cc.abro.tow.client.menu.panels.events.connectbyip;

import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.controllers.connectbyip.ClickConnectController;

public class ClickConnectGuiEvent implements GuiElementEvent {

    private final String ip;
    private final String port;

    public ClickConnectGuiEvent(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    @Override
    public Class<? extends GuiElementController<ClickConnectGuiEvent>> getControllerClass() {
        return ClickConnectController.class;
    }
}
