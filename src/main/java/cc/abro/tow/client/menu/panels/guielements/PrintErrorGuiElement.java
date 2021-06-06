package cc.abro.tow.client.menu.panels.guielements;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Loader;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.gui.*;

import static cc.abro.tow.client.menu.panels.events.MainMenuGuiEvent.MainMenuGuiEventType.*;
import static cc.abro.tow.client.menu.panels.events.MainMenuGuiEvent.MainMenuGuiEventType.CLICK_EXIT;
import static cc.abro.tow.client.menu.panels.events.PrintErrorGuiEvent.MainMenuGuiEventType.CLICK_BUTTON_OK;

public class PrintErrorGuiElement extends EventableGuiElement<PrintErrorGuiPanel> {

    public PrintErrorGuiElement(PrintErrorGuiPanel component) {
        super(component);
        component.addListener(this);
    }

    @Override
    public void processEvent(GuiElementEvent event) {
        if (event.getType() == CLICK_BUTTON_OK){
            destroy();
        }
    }
}
