package cc.abro.tow.client.menu.panels.events;

import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEventType;

import static cc.abro.tow.client.menu.panels.events.ConnectByIPMenuGuiEvent.ConnectByIPMenuGuiEventType.CLICK_BUTTON_CONNECT;

public class ConnectByIPMenuGuiEvent implements GuiElementEvent {

    public String ip;
    public int port;

    private final GuiElementEventType guiElementEventType;

    public ConnectByIPMenuGuiEvent(GuiElementEventType guiElementEventType) {
        this.guiElementEventType = guiElementEventType;
    }

    public ConnectByIPMenuGuiEvent(GuiElementEventType guiElementEventType, String ip, int port) {
        this(guiElementEventType);
        this.ip = ip;
        this.port = port;
    }

    @Override
    public GuiElementEventType getType() {
        return guiElementEventType;
    }

    public enum ConnectByIPMenuGuiEventType implements GuiElementEventType{
        CLICK_BUTTON_BACK, CLICK_BUTTON_CONNECT
    }
}
