package tow.game.client.menu;

import tow.engine.Loader;

import java.util.List;

public class MainMenuLocation extends MenuLocation {

    public MainMenuLocation(){
        createMenuButtons(List.of(
                new ButtonConfiguration("Connect to the game", getActivateLocationMouseReleaseListener(ConnectMenuLocation.class)),
                new ButtonConfiguration("Create a game", getActivateLocationMouseReleaseListener(PlayMenuLocation.class)),
                new ButtonConfiguration("Settings", getActivateLocationMouseReleaseListener(SettingsMenuLocation.class)),
                new ButtonConfiguration("Exit", getMouseReleaseListener(event -> Loader.exit()))
        ));
    }

}
