package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.events.main.ClickExitGuiEvent;

import java.util.Set;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        super(() -> Set.of(new ClickChangePanelController(), new ClickExitController()));
        addMenuButtons(new ButtonConfiguration("Create a game", new ClickChangePanelGuiEvent(CreateGameMenuGuiPanel.class)),
                new ButtonConfiguration("List of servers", new ClickChangePanelGuiEvent(ListOfServersMenuGuiPanel.class)),
                new ButtonConfiguration("Connect via IP", new ClickChangePanelGuiEvent(ConnectByIPMenuGuiPanel.class)),
                new ButtonConfiguration("Settings", new ClickChangePanelGuiEvent(SettingsMenuGuiPanel.class)),
                new ButtonConfiguration("Exit", new ClickExitGuiEvent()));
    }

}
