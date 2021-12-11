package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangeToPanelFromCacheGuiEvent;
import org.liquidengine.legui.component.ScrollablePanel;

import java.util.ArrayList;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;
import static cc.abro.tow.client.menu.MenuGuiService.createButton;

public class ListOfServersMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = (6*INDENT_X + 5*BUTTON_WIDTH) +
            (MENU_ELEMENT_WIDTH > (6*INDENT_X + 5*BUTTON_WIDTH) ? MENU_ELEMENT_WIDTH - (6*INDENT_X + 5*BUTTON_WIDTH) : 0);
    protected final static int MAIN_PANEL_HEIGHT = 7 * MENU_ELEMENT_HEIGHT;
    protected final static int SERVER_NAME_PADDING_X = 5;
    protected final static int SERVER_IP_PADDING_X = 5;
    protected final static int SERVER_PLAYERS_PADDING_X = 5;
    protected final static int SERVER_PING_PADDING_X = 5;
    protected final static int LIST_PADDING_Y = 10;
    protected final static int INDENT_Y = INDENT_X;
    protected final static int INDENT_BUTTON_X = (MAIN_PANEL_WIDTH - 5*BUTTON_WIDTH)/6;

    public class Server {
        String name;
        String IP;
        String playerCount;
        String playerMax;
        String ping;

        public Server(String name, String IP, String playerCount, String playerMax, String ping) {
            this.name = name;
            this.IP = IP;
            this.playerCount = playerCount;
            this.playerMax = playerMax;
            this.ping = ping;
        }
    }

    public ListOfServersMenuGuiPanel() {
        super();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
        add(createBigLabel("List of servers", (MAIN_PANEL_WIDTH - LABEL_LENGTH_LIST_OF_SERVERS)/2, INDENT_Y, LABEL_LENGTH_LIST_OF_SERVERS, MENU_TEXT_FIELD_HEIGHT));
        ScrollablePanel scrollablePanel = createScrollablePanel(INDENT_X, 2*INDENT_Y + MENU_TEXT_FIELD_HEIGHT,
                MAIN_PANEL_WIDTH - 2*INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - MENU_TEXT_FIELD_HEIGHT - 4*INDENT_Y);
        ArrayList<Server> servers = new ArrayList<>();
        /*for(int i = 0; i < servers.size(); i++) {
            scrollablePanel.getContainer().add(createLabel(servers.get(i).name),
                    SERVER_NAME_PADDING_X, LIST_PADDING_Y, );

        }*/

        add(createButton("Back", INDENT_BUTTON_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(MainMenuGuiPanel.class))));
        add(createButton("Add server", 2*INDENT_BUTTON_X + BUTTON_WIDTH, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(MainMenuGuiPanel.class))));
        add(createButton("Edit server", 3*INDENT_BUTTON_X + 2*BUTTON_WIDTH, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(MainMenuGuiPanel.class))));
        add(createButton("Delete server", 4*INDENT_BUTTON_X + 3*BUTTON_WIDTH, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(MainMenuGuiPanel.class))));
        add(createButton("Join the game", 5*INDENT_BUTTON_X + 4*BUTTON_WIDTH, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(MainMenuGuiPanel.class))));
    }
}
