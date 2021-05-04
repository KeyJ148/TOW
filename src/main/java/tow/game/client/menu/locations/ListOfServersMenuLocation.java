package tow.game.client.menu.locations;

import org.liquidengine.legui.component.Panel;
import tow.game.client.menu.MenuLocation;

import static tow.game.client.menu.InterfaceStyles.*;

public class ListOfServersMenuLocation extends MenuLocation {

    protected final static int MAIN_PANEL_WIDTH = 3*MENU_ELEMENT_WIDTH;
    protected final static int MAIN_PANEL_HEIGHT = 7*MENU_ELEMENT_HEIGHT;


    public ListOfServersMenuLocation() {
        Panel mainPanel = createPanel(width/2, height/2, MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        createButton("Back", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X, BUTTON_WIDTH, BUTTON_HEIGHT,
                getActivateLocationMouseReleaseListener(ConnectMenuLocation.class), mainPanel);
    }
}
