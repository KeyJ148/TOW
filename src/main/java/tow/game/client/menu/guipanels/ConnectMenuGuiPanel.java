package tow.game.client.menu.guipanels;

import tow.game.client.menu.MenuGuiPanel;

public class ConnectMenuGuiPanel extends MenuGuiPanel {

    public ConnectMenuGuiPanel() {
        init();
        addMenuButtons(
                new ButtonConfiguration("Connect via IP", getChangeCachedPanelMouseReleaseListener(ConnectByIPMenuGuiPanel.class)),
                new ButtonConfiguration("List of servers", getChangeCachedPanelMouseReleaseListener(ListOfServersMenuGuiPanel.class)),
                new ButtonConfiguration("Back to menu", getChangeCachedPanelMouseReleaseListener(MainMenuGuiPanel.class))
        );

    }
}
