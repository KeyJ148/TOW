package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.events.main.ClickExitGuiEvent;

import java.util.Set;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        init(() -> Set.of(new ClickChangePanelController(), new ClickExitController()));
        addMenuButtons(new ButtonConfiguration("Connect to the game", new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class)),
                new ButtonConfiguration("Create a game", new ClickChangePanelGuiEvent(CreateGameMenuGuiPanel.class)),
                new ButtonConfiguration("Settings", new ClickChangePanelGuiEvent(SettingsMenuGuiPanel.class)),
                new ButtonConfiguration("Exit", new ClickExitGuiEvent()));
    }

}
