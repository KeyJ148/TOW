package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;
import org.liquidengine.legui.component.ScrollablePanel;

import java.util.ArrayList;
import java.util.Set;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class ListOfServersMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 6*INDENT_X + 5*BUTTON_WIDTH;
    protected final static int MAIN_PANEL_HEIGHT = 7 * MENU_ELEMENT_HEIGHT;
    protected final static int SERVER_NAME_PADDING_X = 5;
    protected final static int SERVER_IP_PADDING_X = 5;
    protected final static int SERVER_PLAYERS_PADDING_X = 5;
    protected final static int SERVER_PING_PADDING_X = 5;
    protected final static int LIST_PADDING_Y = 10;
    protected final static int INDENT_Y = INDENT_X;

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
        init(() -> Set.of(new ClickChangePanelController()));
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
        addBigLabel("List of servers", (MAIN_PANEL_WIDTH - LABEL_LENGTH_LIST_OF_SERVERS)/2, INDENT_Y, LABEL_LENGTH_LIST_OF_SERVERS, MENU_TEXT_FIELD_HEIGHT);
        ScrollablePanel scrollablePanel = createScrollablePanel(INDENT_X, 2*INDENT_Y + MENU_TEXT_FIELD_HEIGHT,
                MAIN_PANEL_WIDTH - 2*INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - MENU_TEXT_FIELD_HEIGHT - 4*INDENT_Y);
        ArrayList<Server> servers = new ArrayList<>();
        /*for(int i = 0; i < servers.size(); i++) {
            scrollablePanel.getContainer().add(createLabel(servers.get(i).name),
                    SERVER_NAME_PADDING_X, LIST_PADDING_Y, );

        }*/

        addButton("Back", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X, BUTTON_WIDTH, BUTTON_HEIGHT,
                () -> new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class));
        addButton("Create server", 2*INDENT_X + BUTTON_WIDTH, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X,
                BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class));
        addButton("Edit server", 3*INDENT_X + 2*BUTTON_WIDTH, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X,
                BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class));
        addButton("Delete server", 4*INDENT_X + 3*BUTTON_WIDTH, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X,
                BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class));
        addButton("Join the game", 5*INDENT_X + 4*BUTTON_WIDTH, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X,
                BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class));
    }
}
