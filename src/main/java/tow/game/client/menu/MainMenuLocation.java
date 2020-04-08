package tow.game.client.menu;

import org.liquidengine.legui.event.MouseClickEvent;
import tow.engine.Loader;

import java.util.List;

public class MainMenuLocation extends MenuLocation {

    public MainMenuLocation(){
        createMenuButtons(List.of(
                new ButtonConfiguration("Connect to the game", getActivateLocationMouseReleaseListener(ConnectMenuLocation.class)),
                new ButtonConfiguration("Create a game", getActivateLocationMouseReleaseListener(PlayMenuLocation.class)),
                new ButtonConfiguration("Settings", getActivateLocationMouseReleaseListener(SettingsMenuLocation.class)),
                new ButtonConfiguration("Exit",
                        event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                            Loader.exit();
                        }})
                ));
    }

}
