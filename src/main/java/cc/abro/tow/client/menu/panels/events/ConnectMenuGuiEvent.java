package cc.abro.tow.client.menu.panels.events;

import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEventType;

public class ConnectMenuGuiEvent implements GuiElementEvent {

    private final GuiElementEventType guiElementEventType;

    public ConnectMenuGuiEvent(GuiElementEventType guiElementEventType){
        this.guiElementEventType = guiElementEventType;
    }

    @Override
    public GuiElementEventType getType() {
        return guiElementEventType;
    }

    public enum ConnectMenuGuiEventType implements GuiElementEventType{
        CLICK_CONNECT_BY_IP, CLICK_LIST_OF_SERVERS, CLICK_BACK
    }
}
