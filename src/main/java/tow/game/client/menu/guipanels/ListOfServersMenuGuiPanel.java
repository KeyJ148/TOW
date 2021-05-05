package tow.game.client.menu.guipanels;

import tow.game.client.menu.MenuGuiPanel;

import static tow.game.client.menu.InterfaceStyles.*;

public class ListOfServersMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 3*MENU_ELEMENT_WIDTH;
    protected final static int MAIN_PANEL_HEIGHT = 7*MENU_ELEMENT_HEIGHT;

    public ListOfServersMenuGuiPanel() {
        init();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        addButton("Back", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X, BUTTON_WIDTH, BUTTON_HEIGHT,
                getChangeCachedPanelMouseReleaseListener(ConnectMenuGuiPanel.class));

    }
}
