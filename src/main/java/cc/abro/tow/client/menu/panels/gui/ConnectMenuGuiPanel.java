package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.events.MainMenuGuiEvent;

import static cc.abro.tow.client.menu.panels.events.MainMenuGuiEvent.MainMenuGuiEventType.CLICK_CONNECT;
import static cc.abro.tow.client.menu.panels.events.MainMenuGuiEvent.MainMenuGuiEventType.CLICK_EXIT;

public class ConnectMenuGuiPanel extends MenuGuiPanel {

    public ConnectMenuGuiPanel() {
        init();
        addMenuButtons(
                new ButtonConfiguration("Connect via IP", new MainMenuGuiEvent(CLICK_EXIT)),//TODO changle listeners
                new ButtonConfiguration("List of servers", new MainMenuGuiEvent(CLICK_EXIT)),
                new ButtonConfiguration("Back to menu", new MainMenuGuiEvent(CLICK_EXIT))
        );

    }
}
