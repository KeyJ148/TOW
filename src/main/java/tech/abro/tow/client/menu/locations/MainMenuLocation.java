package tech.abro.tow.client.menu.locations;

import tech.abro.orchengine.Loader;
import tech.abro.tow.client.menu.MenuLocation;

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
