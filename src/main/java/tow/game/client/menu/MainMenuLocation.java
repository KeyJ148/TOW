package tow.game.client.menu;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import tow.engine.Loader;

import java.util.List;

import static tow.game.client.menu.InterfaceStyles.*;

public class MainMenuLocation extends MenuLocation {

    public MainMenuLocation(){
        createMenuButtons(List.of(
                new ButtonConfiguration("Connect to the game", event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new ConnectMenuLocation();}),
                new ButtonConfiguration("Create a game", event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new PlayMenuLocation();}),
                new ButtonConfiguration("Settings", event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new SettingsMenuLocation();}),
                new ButtonConfiguration("Exit", event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) Loader.exit();})
                ));
    }

}
