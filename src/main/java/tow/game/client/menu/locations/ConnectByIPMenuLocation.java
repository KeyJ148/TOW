package tow.game.client.menu.locations;

import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import tow.engine.net.client.Connector;
import tow.game.client.menu.MenuLocation;

import static tow.game.client.menu.InterfaceStyles.*;

public class ConnectByIPMenuLocation extends MenuLocation {

    private boolean wasConnect = false;

    protected final static int MAIN_PANEL_WIDTH = 4*MENU_ELEMENT_WIDTH/3;
    protected final static int MAIN_PANEL_HEIGHT = 5*MENU_ELEMENT_HEIGHT/3;
    protected final static int INDENT_Y_BELOW = 3* MAIN_PANEL_HEIGHT /5;
    protected final static int INDENT_Y_ABOVE = MAIN_PANEL_HEIGHT /5;
    protected final static int LENGTH_TEXT_AREA_IP = 95;
    protected final static int LENGTH_TEXT_AREA_PORT = 38;

    public ConnectByIPMenuLocation() {
        Panel mainPanel = createPanel(width/2, height/2, MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        addComponentToParentLU(new Label("IP:"), INDENT_X, INDENT_Y_ABOVE, 150, MENU_TEXT_FIELD_HEIGHT, mainPanel);

        TextAreaField textAreaFieldIP = createTextAreaField(INDENT_X + 20, INDENT_Y_ABOVE, LENGTH_TEXT_AREA_IP, MENU_TEXT_FIELD_HEIGHT, mainPanel);

        addComponentToParentLU(new Label("Port:"), MAIN_PANEL_WIDTH - LENGTH_TEXT_AREA_PORT - INDENT_X - 30, INDENT_Y_ABOVE, 150, MENU_TEXT_FIELD_HEIGHT, mainPanel);

        TextAreaField textAreaFieldPort = createTextAreaField(MAIN_PANEL_WIDTH - LENGTH_TEXT_AREA_PORT - INDENT_X, INDENT_Y_ABOVE, LENGTH_TEXT_AREA_PORT, MENU_TEXT_FIELD_HEIGHT, mainPanel);

        textAreaFieldPort.getTextState().setText("25566");

        createButton("Back", INDENT_X, INDENT_Y_BELOW, BUTTON_WIDTH, BUTTON_HEIGHT,
                getActivateLocationMouseReleaseListener(ConnectMenuLocation.class), mainPanel);
        createButton("Connect", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, INDENT_Y_BELOW, BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    if (wasConnect) return;
                    wasConnect = true;

                    String ip = (!textAreaFieldIP.getTextState().getText().isEmpty())? textAreaFieldIP.getTextState().getText() : "127.0.0.1";
                    int port = Integer.parseInt(textAreaFieldPort.getTextState().getText());

                    new Connector().connect(ip, port);
                }), mainPanel);
    }

}
