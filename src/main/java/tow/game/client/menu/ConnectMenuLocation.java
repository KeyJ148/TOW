package tow.game.client.menu;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.event.MouseClickEvent;

import static tow.game.client.menu.InterfaceStyles.*;

public class ConnectMenuLocation extends MenuLocation {

    public ConnectMenuLocation() {
        createMenuButton("Connect via IP", -1, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new ConnectByIPMenuLocation();});
        createMenuButton("List of servers", 0, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new ListOfServersMenuLocation();});
        createMenuButton("Back to menu", 1, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new MainMenuLocation();});

    }
}
