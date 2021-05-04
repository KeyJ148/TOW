package tow.game.client.menu.locations;

import tow.game.client.menu.MenuGuiPanel;

public class ConnectMenuGuiPanel extends MenuGuiPanel {

    public ConnectMenuGuiPanel() {
        init();
        addMenuButtons(
                //new ButtonConfiguration("Connect via IP", getChangePanelMouseReleaseListener(ConnectByIPMenuLocation.class)),
                //new ButtonConfiguration("List of servers", getChangePanelMouseReleaseListener(ListOfServersMenuLocation.class)),
                new ButtonConfiguration("Back to menu", getChangeCachedPanelMouseReleaseListener(MainMenuGuiPanel.class))
        );

    }
}
