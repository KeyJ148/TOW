package tow.game.client.menu.locations;

import tow.engine.Loader;
import tow.game.client.menu.MenuLocation;

public class MainMenuLocation extends MenuLocation {

    public MainMenuLocation(){
        createMenuButtons(
                new ButtonConfiguration("Connect to the game", getActivateLocationMouseReleaseListener(ConnectMenuLocation.class)),
                new ButtonConfiguration("Create a game", getActivateLocationMouseReleaseListener(CreateGameMenuLocation.class)),
                new ButtonConfiguration("Settings", getActivateLocationMouseReleaseListener(SettingsMenuLocation.class)),
                new ButtonConfiguration("Exit", getMouseReleaseListener(event -> Loader.exit()))
        );
    }

}
