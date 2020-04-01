package tow.game.client.menu;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import tow.engine.Loader;

import static tow.game.client.menu.InterfaceStyles.*;

public class MainMenuLocation extends MenuLocation {

    public MainMenuLocation(){
        createMenuButton("Connect to the game", -2, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new ConnectMenuLocation();});
        createMenuButton("Play", -1, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new PlayMenuLocation();});
        createMenuButton("Settings", 0, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new SettingsMenuLocation();});
        createMenuButton("Exit", 1, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) Loader.exit();});
    }

}
