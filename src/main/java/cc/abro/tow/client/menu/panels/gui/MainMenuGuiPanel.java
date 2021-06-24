package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.events.ClickChangePanelGuiEvent;
import cc.abro.tow.client.menu.panels.events.main.ClickExitGuiEvent;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        init();
        addMenuButtons(new ButtonConfiguration("Connect to the game", new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class)),
                new ButtonConfiguration("Create a game", new ClickChangePanelGuiEvent(CreateGameMenuGuiPanel.class)),
                new ButtonConfiguration("Settings", new ClickChangePanelGuiEvent(SettingsMenuGuiPanel.class)),
                new ButtonConfiguration("Exit", new ClickExitGuiEvent()));
    }

}
