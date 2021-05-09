package cc.abro.tow.client.menu.panels.guielements;

import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gameobject.components.gui.GuiPanelElement;
import cc.abro.tow.client.menu.panels.gui.ConnectMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MenuGuiPanel;

public class ConnectMenuGuiElement extends GuiPanelElement<ConnectMenuGuiPanel> {

    public ConnectMenuGuiElement(ConnectMenuGuiPanel component) {
        super(component);
        component.addListener(this);
    }

    @Override
    public void processEvent(GuiElementEvent event) {

    }
}
