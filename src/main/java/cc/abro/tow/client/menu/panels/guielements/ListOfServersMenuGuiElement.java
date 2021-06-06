package cc.abro.tow.client.menu.panels.guielements;

import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.gui.ListOfServersMenuGuiPanel;

public class ListOfServersMenuGuiElement extends EventableGuiElement<ListOfServersMenuGuiPanel> {

    ListOfServersMenuGuiElement(ListOfServersMenuGuiPanel component) {
        super(component);
        component.addListener(this);
    }

    @Override
    public void processEvent(GuiElementEvent event) {

    }
}
