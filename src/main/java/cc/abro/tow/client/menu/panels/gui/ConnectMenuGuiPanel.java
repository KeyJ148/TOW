package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.events.ConnectMenuGuiEvent;

import static cc.abro.tow.client.menu.panels.events.ConnectMenuGuiEvent.ConnectMenuGuiEventType.*;

public class ConnectMenuGuiPanel extends MenuGuiPanel {

    public ConnectMenuGuiPanel() {
        init();
        addMenuButtons(
                new ButtonConfiguration("Connect via IP", new ConnectMenuGuiEvent(CLICK_CONNECT_BY_IP)),
                new ButtonConfiguration("List of servers", new ConnectMenuGuiEvent(CLICK_LIST_OF_SERVERS)),
                new ButtonConfiguration("Back to menu", new ConnectMenuGuiEvent(CLICK_BACK))
        );

    }
}
