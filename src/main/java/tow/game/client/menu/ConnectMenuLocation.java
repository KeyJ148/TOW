package tow.game.client.menu;

import org.liquidengine.legui.event.MouseClickEvent;
import tow.game.client.ClientData;

import java.util.List;

public class ConnectMenuLocation extends MenuLocation {

    public ConnectMenuLocation() {
        createMenuButtons(List.of(
                new ButtonConfiguration("Connect via IP",
                        event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                            ClientData.menuLocationStorage.getMenuLocation(ConnectByIPMenuLocation.class).activate();
                        }}),
                new ButtonConfiguration("List of servers",
                        event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE){
                            ClientData.menuLocationStorage.getMenuLocation(ListOfServersMenuLocation.class).activate();
                        }}),
                new ButtonConfiguration("Back to menu",
                        event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                            ClientData.menuLocationStorage.getMenuLocation(MainMenuLocation.class).activate();
                        }})
        ));

    }
}
