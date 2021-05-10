package cc.abro.tow.client.menu.panels.guielements;

import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.tow.client.menu.panels.gui.ConnectMenuGuiPanel;

public class ConnectMenuGuiElement extends EventableGuiElement<ConnectMenuGuiPanel> {

    public ConnectMenuGuiElement(ConnectMenuGuiPanel component) {
        super(component);
        component.addListener(this);
    }

    @Override
    public void processEvent(GuiElementEvent event) {

    }
}
