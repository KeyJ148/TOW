package tow.game.client.menu.guipanels;

import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.TextAreaField;
import tow.engine.net.client.Connector;
import tow.game.client.menu.MenuGuiPanel;

import static tow.game.client.menu.InterfaceStyles.*;

public class ConnectByIPMenuGuiPanel extends MenuGuiPanel {

    private boolean wasConnect = false;

    protected final static int MAIN_PANEL_WIDTH = 4*MENU_ELEMENT_WIDTH/3;
    protected final static int MAIN_PANEL_HEIGHT = 5*MENU_ELEMENT_HEIGHT/3;
    protected final static int INDENT_Y_BELOW = 3* MAIN_PANEL_HEIGHT /5;
    protected final static int INDENT_Y_ABOVE = MAIN_PANEL_HEIGHT /5;
    protected final static int LENGTH_TEXT_AREA_IP = 95;
    protected final static int LENGTH_TEXT_AREA_PORT = 38;

    public ConnectByIPMenuGuiPanel() {
        init();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        add(new Label("IP:", INDENT_X, INDENT_Y_ABOVE, 150, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldIP =
                createTextAreaField(INDENT_X + 20, INDENT_Y_ABOVE, LENGTH_TEXT_AREA_IP, MENU_TEXT_FIELD_HEIGHT);

        add(new Label("Port:", MAIN_PANEL_WIDTH - LENGTH_TEXT_AREA_PORT - INDENT_X - 30, INDENT_Y_ABOVE, 150, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldPort =
                createTextAreaField(MAIN_PANEL_WIDTH - LENGTH_TEXT_AREA_PORT - INDENT_X, INDENT_Y_ABOVE, LENGTH_TEXT_AREA_PORT, MENU_TEXT_FIELD_HEIGHT);
        textAreaFieldPort.getTextState().setText("25566");

        addButton("Back", INDENT_X, INDENT_Y_BELOW, BUTTON_WIDTH, BUTTON_HEIGHT,
                getChangeCachedPanelMouseReleaseListener(ConnectMenuGuiPanel.class));
        addButton("Connect", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, INDENT_Y_BELOW, BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    if (wasConnect) return;
                    wasConnect = true;

                    String ip = (!textAreaFieldIP.getTextState().getText().isEmpty())? textAreaFieldIP.getTextState().getText() : "127.0.0.1";
                    int port = Integer.parseInt(textAreaFieldPort.getTextState().getText());

                    new Connector().connect(ip, port);
                }));

    }
}
