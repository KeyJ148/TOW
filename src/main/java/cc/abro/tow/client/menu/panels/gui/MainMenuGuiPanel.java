package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.controllers.main.ClickConnectController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickHostController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickSettingsController;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        init();
        addMenuButtons(new ButtonConfiguration("Connect to the game", () -> ClickConnectController.class),
                new ButtonConfiguration("Create a game", () -> ClickHostController.class),
                new ButtonConfiguration("Settings", () -> ClickSettingsController.class),
                new ButtonConfiguration("Exit", () -> ClickExitController.class));
    }

}
