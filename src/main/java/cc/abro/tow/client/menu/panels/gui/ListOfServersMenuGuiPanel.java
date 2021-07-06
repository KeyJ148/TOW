package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;

import java.util.Set;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class ListOfServersMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 2 * MENU_ELEMENT_WIDTH;
    protected final static int MAIN_PANEL_HEIGHT = 7 * MENU_ELEMENT_HEIGHT;
    protected final static int INDENT_Y = INDENT_X;

    public ListOfServersMenuGuiPanel() {
        init(() -> Set.of(new ClickChangePanelController()));
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
        createScrollablePanel(INDENT_X, INDENT_Y, MAIN_PANEL_WIDTH - 2*INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - 3*INDENT_Y);
        addButton("Back", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X, BUTTON_WIDTH, BUTTON_HEIGHT,
                () -> new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class));

    }
}
