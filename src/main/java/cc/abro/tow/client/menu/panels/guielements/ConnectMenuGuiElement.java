package cc.abro.tow.client.menu.panels.guielements;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.tow.client.menu.panels.gui.ConnectByIPMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.ConnectMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.ListOfServersMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;

import static cc.abro.tow.client.menu.panels.events.ConnectMenuGuiEvent.ConnectMenuGuiEventType.*;

public class ConnectMenuGuiElement extends EventableGuiElement<ConnectMenuGuiPanel> {

    public ConnectMenuGuiElement(ConnectMenuGuiPanel component) {
        super(component);
        component.addListener(this);
    }

    @Override
    public void processEvent(GuiElementEvent event) {
        if(event.getType() == CLICK_CONNECT_BY_IP) {
            ConnectByIPMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(ConnectByIPMenuGuiPanel.class);
            ConnectByIPMenuGuiElement guiElement = new ConnectByIPMenuGuiElement(guiPanel);
            destroyAndCreateGuiElement(guiElement);
        }

        if(event.getType() == CLICK_LIST_OF_SERVERS) {
            ListOfServersMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(ListOfServersMenuGuiPanel.class);
            ListOfServersMenuGuiElement guiElement = new ListOfServersMenuGuiElement(guiPanel);
            destroyAndCreateGuiElement(guiElement);
        }

        if(event.getType() == CLICK_BACK) {
            MainMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(MainMenuGuiPanel.class);
            MainMenuGuiElement guiElement = new MainMenuGuiElement(guiPanel);
            destroyAndCreateGuiElement(guiElement);
        }
    }
}
