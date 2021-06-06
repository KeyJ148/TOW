package cc.abro.tow.client.menu.panels.events.connectbyip;

import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.controllers.connectbyip.ClickConnectController;

public class ClickConnectGuiEvent implements GuiElementEvent {

    private final String ip;
    private final int port;

    public ClickConnectGuiEvent(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public Class<? extends GuiElementController> getControllerClass() {
        return ClickConnectController.class;
    }
}
