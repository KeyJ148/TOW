package tow.game.client.menu;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.event.MouseClickEvent;
import tow.engine.Loader;

import java.util.List;

import static tow.game.client.menu.InterfaceStyles.*;

public class ConnectMenuLocation extends MenuLocation {

    public ConnectMenuLocation() {
        createMenuButtons(List.of(
                new ButtonConfiguration("Connect via IP", event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new ConnectByIPMenuLocation();}),
                new ButtonConfiguration("List of servers", event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new ListOfServersMenuLocation();}),
                new ButtonConfiguration("Back to menu", event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new MainMenuLocation();})
        ));

    }
}
