package tow.game.client.menu.locations;

import tow.game.client.menu.MenuLocation;

public class ConnectMenuLocation extends MenuLocation {

    public ConnectMenuLocation() {
        createMenuButtons(
                new ButtonConfiguration("Connect via IP", getActivateLocationMouseReleaseListener(ConnectByIPMenuLocation.class)),
                new ButtonConfiguration("List of servers", getActivateLocationMouseReleaseListener(ListOfServersMenuLocation.class)),
                new ButtonConfiguration("Back to menu", getActivateLocationMouseReleaseListener(MainMenuLocation.class))
        );

    }
}
