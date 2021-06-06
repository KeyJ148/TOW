package cc.abro.tow.client.menu.panels.events;

import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEventType;
import cc.abro.tow.client.menu.panels.gui.CreateGameMenuGuiPanel;

public class CreateGameMenuGuiEvent implements GuiElementEvent {

    private final GuiElementEventType guiElementEventType;
    public String port;
    public int maxPeople;

    public CreateGameMenuGuiEvent(GuiElementEventType guiElementEventType) {
        this.guiElementEventType = guiElementEventType;
    }

    public CreateGameMenuGuiEvent(GuiElementEventType guiElementEventType, String port, int maxPeople) {
        this(guiElementEventType);
        this.port = port;
        this.maxPeople = maxPeople;
    }

    @Override
    public GuiElementEventType getType() {
        return guiElementEventType;
    }

    public enum CreateGameMenuGuiEventType implements GuiElementEventType{
        CLICK_BUTTON_BACK, CLICK_BUTTON_CREATE
    }
}
