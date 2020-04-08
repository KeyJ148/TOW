package tow.game.client.menu;

import java.util.List;

public class ConnectMenuLocation extends MenuLocation {

    public ConnectMenuLocation() {
        createMenuButtons(List.of(
                new ButtonConfiguration("Connect via IP", getActivateLocationMouseReleaseListener(ConnectByIPMenuLocation.class)),
                new ButtonConfiguration("List of servers", getActivateLocationMouseReleaseListener(ListOfServersMenuLocation.class)),
                new ButtonConfiguration("Back to menu", getActivateLocationMouseReleaseListener(MainMenuLocation.class))
        ));

    }
}
