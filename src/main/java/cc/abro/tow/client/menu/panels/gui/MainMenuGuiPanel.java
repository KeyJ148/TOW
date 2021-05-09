package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.events.MainMenuGuiEvent;

import static cc.abro.tow.client.menu.panels.events.MainMenuGuiEvent.MainMenuGuiEventType.*;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        init();
        addMenuButtons(new ButtonConfiguration("Connect to the game", new MainMenuGuiEvent(CLICK_CONNECT)),
                new ButtonConfiguration("Create a game", new MainMenuGuiEvent(CLICK_HOST)),
                new ButtonConfiguration("Settings", new MainMenuGuiEvent(CLICK_SETTINGS)),
                new ButtonConfiguration("Exit", new MainMenuGuiEvent(CLICK_EXIT)));
    }

}
