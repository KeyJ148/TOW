package tow.game.client.menu;

import org.liquidengine.legui.event.MouseClickEvent;
import tow.engine.Loader;
import tow.game.client.ClientData;

import java.util.List;

public class MainMenuLocation extends MenuLocation {

    public MainMenuLocation(){
        createMenuButtons(List.of(
                new ButtonConfiguration("Connect to the game",
                        event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                            ClientData.menuLocationStorage.getMenuLocation(ConnectMenuLocation.class).activate();
                        }}),
                new ButtonConfiguration("Create a game",
                        event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                            ClientData.menuLocationStorage.getMenuLocation(PlayMenuLocation.class).activate();
                        }}),
                new ButtonConfiguration("Settings",
                        event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                            ClientData.menuLocationStorage.getMenuLocation(SettingsMenuLocation.class).activate();
                        }}),
                new ButtonConfiguration("Exit",
                        event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                            Loader.exit();
                        }})
                ));
    }

}
