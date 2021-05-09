package cc.abro.tow.client.menu.panels.events;

import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEventType;

public class MainMenuGuiEvent implements GuiElementEvent {

    private final GuiElementEventType guiElementEventType;

    public MainMenuGuiEvent(GuiElementEventType guiElementEventType){
        this.guiElementEventType = guiElementEventType;
    }

    @Override
    public GuiElementEventType getType() {
        return guiElementEventType;
    }

    public enum MainMenuGuiEventType implements GuiElementEventType{
        CLICK_CONNECT, CLICK_HOST, CLICK_SETTINGS, CLICK_EXIT
    }
}
