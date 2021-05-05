package cc.abro.tow.client.menu.guipanels;

import cc.abro.tow.client.menu.MenuGuiPanel;

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
