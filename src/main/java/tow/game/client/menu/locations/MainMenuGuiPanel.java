package tow.game.client.menu.locations;

import tow.engine.Loader;
import tow.game.client.menu.MenuGuiPanel;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        init();
        addMenuButtons(new ButtonConfiguration("Connect to the game", getChangeCachedPanelMouseReleaseListener(ConnectMenuGuiPanel.class)),
                //new ButtonConfiguration("Create a game", getChangeCachedPanelMouseReleaseListener(this, CreateGameMenuLocation.class)),
                //new ButtonConfiguration("Settings", getChangeCachedPanelMouseReleaseListener(this, SettingsMenuLocation.class)),
                new ButtonConfiguration("Exit", getMouseReleaseListener(event -> Loader.exit())));
    }

}
