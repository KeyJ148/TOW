package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.events.main.ClickExitGuiEvent;

import java.util.Set;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        super(() -> Set.of(new ClickChangeToPanelFromCacheController(), new ClickChangePanelController(), new ClickExitController()));
        addMenuButtons(new ButtonConfiguration("Create a game", new ClickChangeToPanelFromCacheGuiEvent(CreateGameMenuGuiPanel.class)),
                new ButtonConfiguration("List of servers", new ClickChangeToPanelFromCacheGuiEvent(ListOfServersMenuGuiPanel.class)),
                new ButtonConfiguration("Connect via IP", new ClickChangeToPanelFromCacheGuiEvent(ConnectByIPMenuGuiPanel.class)),
                new ButtonConfiguration("Settings", new ClickChangePanelGuiEvent(() -> new SettingsMenuGuiPanel(),
                        () -> Set.of(new ClickChangeToPanelFromCacheController(), new ClickConfirmController()))),
                new ButtonConfiguration("Exit", new ClickExitGuiEvent()));
    }

}
