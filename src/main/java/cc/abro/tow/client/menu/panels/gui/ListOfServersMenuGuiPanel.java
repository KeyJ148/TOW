package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;
import org.liquidengine.legui.component.ScrollablePanel;

import java.util.Set;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class ListOfServersMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 2 * MENU_ELEMENT_WIDTH;
    protected final static int MAIN_PANEL_HEIGHT = 7 * MENU_ELEMENT_HEIGHT;
    protected final static int INDENT_Y = INDENT_X;

    public ListOfServersMenuGuiPanel() {
        init(() -> Set.of(new ClickChangePanelController()));
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
        addBigLabel("List of servers", (MAIN_PANEL_WIDTH - LABEL_LENGTH_LIST_OF_SERVERS)/2, INDENT_Y, LABEL_LENGTH_LIST_OF_SERVERS, MENU_TEXT_FIELD_HEIGHT);
        ScrollablePanel scrollablePanel = createScrollablePanel(INDENT_X, 2*INDENT_Y + MENU_TEXT_FIELD_HEIGHT,
                MAIN_PANEL_WIDTH - 2*INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - MENU_TEXT_FIELD_HEIGHT - 4*INDENT_Y);
        addButton("Back", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X, BUTTON_WIDTH, BUTTON_HEIGHT,
                () -> new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class));

    }
}
