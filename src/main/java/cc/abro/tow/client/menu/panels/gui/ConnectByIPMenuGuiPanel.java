package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.net.client.Connector;
import cc.abro.tow.client.menu.panels.events.ConnectByIPMenuGuiEvent;
import org.liquidengine.legui.component.TextAreaField;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.panels.events.ConnectByIPMenuGuiEvent.ConnectByIPMenuGuiEventType.CLICK_BUTTON_BACK;
import static cc.abro.tow.client.menu.panels.events.ConnectByIPMenuGuiEvent.ConnectByIPMenuGuiEventType.CLICK_BUTTON_CONNECT;

public class ConnectByIPMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 4 * MENU_ELEMENT_WIDTH / 3;
    protected final static int MAIN_PANEL_HEIGHT = 5 * MENU_ELEMENT_HEIGHT / 3;

    public ConnectByIPMenuGuiPanel() {
        init();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        addLabel("IP:", INDENT_X, INDENT_Y, LABEL_LENGTH_ID, MENU_TEXT_FIELD_HEIGHT);
        TextAreaField textAreaFieldIP =
                createTextAreaField(INDENT_X + LABEL_LENGTH_ID, INDENT_Y, LENGTH_TEXT_AREA_IP, MENU_TEXT_FIELD_HEIGHT);

        addLabel("Port:", MAIN_PANEL_WIDTH - LABEL_LENGTH_PORT - LENGTH_TEXT_AREA_PORT - INDENT_X, INDENT_Y, 30, MENU_TEXT_FIELD_HEIGHT);
        TextAreaField textAreaFieldPort =
                createTextAreaField(MAIN_PANEL_WIDTH - LENGTH_TEXT_AREA_PORT - INDENT_X, INDENT_Y, LENGTH_TEXT_AREA_PORT, MENU_TEXT_FIELD_HEIGHT, "25566");

        addButton("Back", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                new ConnectByIPMenuGuiEvent(CLICK_BUTTON_BACK));
        addButton("Connect", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                new ConnectByIPMenuGuiEvent(
                        CLICK_BUTTON_CONNECT,
                        (!textAreaFieldIP.getTextState().getText().isEmpty()) ? textAreaFieldIP.getTextState().getText() : null,
                        Integer.parseInt(textAreaFieldPort.getTextState().getText())
                ));
    }
}
