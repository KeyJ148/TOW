package cc.abro.tow.client.menu.panels.guielements;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.client.menu.panels.events.ConnectByIPMenuGuiEvent;
import cc.abro.tow.client.menu.panels.gui.ConnectByIPMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.ConnectMenuGuiPanel;

import static cc.abro.tow.client.menu.panels.events.ConnectByIPMenuGuiEvent.ConnectByIPMenuGuiEventType.CLICK_BUTTON_BACK;
import static cc.abro.tow.client.menu.panels.events.ConnectByIPMenuGuiEvent.ConnectByIPMenuGuiEventType.CLICK_BUTTON_CONNECT;

public class ConnectByIPMenuGuiElement extends EventableGuiElement<ConnectByIPMenuGuiPanel> {

    private boolean wasConnect = false;

    ConnectByIPMenuGuiElement(ConnectByIPMenuGuiPanel component) {
        super(component);
        component.addListener(this);
    }

    @Override
    public void processEvent(GuiElementEvent event) {
        if(event.getType() == CLICK_BUTTON_BACK) {
            ConnectMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(ConnectMenuGuiPanel.class);
            ConnectMenuGuiElement guiElement = new ConnectMenuGuiElement(guiPanel);
            destroyAndCreateGuiElement(guiElement);
        }
        if(event.getType() == CLICK_BUTTON_CONNECT) {
            ConnectByIPMenuGuiEvent reinterpret_event = (ConnectByIPMenuGuiEvent) event;
            if (wasConnect) return;
            wasConnect = true;
            if(reinterpret_event.ip == null) reinterpret_event.ip = "127.0.0.1";
            new Connector().connect(reinterpret_event.ip, reinterpret_event.port);
        }
    }
}
