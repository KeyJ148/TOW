package cc.abro.tow.client.menu.guipanels;

import cc.abro.orchengine.Loader;
import cc.abro.tow.client.menu.MenuGuiPanel;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        init();
        addMenuButtons(new ButtonConfiguration("Connect to the game", getChangeCachedPanelMouseReleaseListener(ConnectMenuGuiPanel.class)),
                new ButtonConfiguration("Create a game", getChangeCachedPanelMouseReleaseListener(CreateGameMenuGuiPanel.class)),
                new ButtonConfiguration("Settings", getChangeCachedPanelMouseReleaseListener(SettingsMenuGuiPanel.class)),
                new ButtonConfiguration("Exit", getMouseReleaseListener(event -> Loader.exit())));
    }

}
