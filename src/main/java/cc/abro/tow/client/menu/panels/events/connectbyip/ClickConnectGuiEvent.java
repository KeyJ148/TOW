package cc.abro.tow.client.menu.panels.events.connectbyip;

import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;

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
}
