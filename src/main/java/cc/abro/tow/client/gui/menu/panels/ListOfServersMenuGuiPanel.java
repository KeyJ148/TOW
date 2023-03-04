package cc.abro.tow.client.gui.menu.panels;

import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import com.spinyowl.legui.component.ScrollablePanel;

import java.util.ArrayList;

public class ListOfServersMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = (6* InterfaceStyles.INDENT_X + 5* InterfaceStyles.BUTTON_WIDTH) +
            (InterfaceStyles.MENU_ELEMENT_WIDTH > (6* InterfaceStyles.INDENT_X + 5* InterfaceStyles.BUTTON_WIDTH) ? InterfaceStyles.MENU_ELEMENT_WIDTH - (6* InterfaceStyles.INDENT_X + 5* InterfaceStyles.BUTTON_WIDTH) : 0);
    protected final static int MAIN_PANEL_HEIGHT = 7 * InterfaceStyles.MENU_ELEMENT_HEIGHT;
    protected final static int SERVER_NAME_PADDING_X = 5;
    protected final static int SERVER_IP_PADDING_X = 5;
    protected final static int SERVER_PLAYERS_PADDING_X = 5;
    protected final static int SERVER_PING_PADDING_X = 5;
    protected final static int LIST_PADDING_Y = 10;
    protected final static int INDENT_Y = InterfaceStyles.INDENT_X;
    protected final static int INDENT_BUTTON_X = (MAIN_PANEL_WIDTH - 5* InterfaceStyles.BUTTON_WIDTH)/6;

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
        add(MenuGuiComponents.createBigLabel("List of servers", (MAIN_PANEL_WIDTH - InterfaceStyles.LABEL_LENGTH_LIST_OF_SERVERS)/2, INDENT_Y, InterfaceStyles.LABEL_LENGTH_LIST_OF_SERVERS, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        ScrollablePanel scrollablePanel = MenuGuiComponents.createScrollablePanel(InterfaceStyles.INDENT_X, 2*INDENT_Y + InterfaceStyles.MENU_TEXT_FIELD_HEIGHT,
                MAIN_PANEL_WIDTH - 2* InterfaceStyles.INDENT_X, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.MENU_TEXT_FIELD_HEIGHT - 4*INDENT_Y);
        add(scrollablePanel);
        ArrayList<Server> servers = new ArrayList<>();
        /*TODO for(int i = 0; i < servers.size(); i++) {
            scrollablePanel.getContainer().add(createLabel(servers.get(i).name),
                    SERVER_NAME_PADDING_X, LIST_PADDING_Y, );

        }*/

        add(MenuGuiComponents.createButton("Back", INDENT_BUTTON_X, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - INDENT_Y, InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class)));
        add(MenuGuiComponents.createButton("Add server", 2*INDENT_BUTTON_X + InterfaceStyles.BUTTON_WIDTH, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class)));
        add(MenuGuiComponents.createButton("Edit server", 3*INDENT_BUTTON_X + 2* InterfaceStyles.BUTTON_WIDTH, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class)));
        add(MenuGuiComponents.createButton("Delete server", 4*INDENT_BUTTON_X + 3* InterfaceStyles.BUTTON_WIDTH, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class)));
        add(MenuGuiComponents.createButton("Join the game", 5*INDENT_BUTTON_X + 4* InterfaceStyles.BUTTON_WIDTH, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class)));
    }
}
