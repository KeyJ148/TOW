package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangeToPanelFromCacheGuiEvent;
import cc.abro.tow.client.menu.panels.events.connectbyip.ClickConnectGuiEvent;
import org.liquidengine.legui.component.TextAreaField;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.createLabel;
import static cc.abro.tow.client.menu.MenuGuiComponents.createTextAreaField;
import static cc.abro.tow.client.menu.MenuGuiService.createButton;

public class ConnectByIPMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 4 * MENU_ELEMENT_WIDTH / 3;
    protected final static int MAIN_PANEL_HEIGHT = 5 * MENU_ELEMENT_HEIGHT / 3;

    public ConnectByIPMenuGuiPanel() {
        super();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        add(createLabel("IP:", INDENT_X, INDENT_Y, LABEL_LENGTH_ID, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldIP =
                createTextAreaField(INDENT_X + LABEL_LENGTH_ID, INDENT_Y, TEXT_AREA_LENGTH_IP, MENU_TEXT_FIELD_HEIGHT);
        add(textAreaFieldIP);

        add(createLabel("Port:", MAIN_PANEL_WIDTH - LABEL_LENGTH_PORT - TEXT_AREA_LENGTH_PORT - INDENT_X, INDENT_Y, LABEL_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldPort =
                createTextAreaField(MAIN_PANEL_WIDTH - TEXT_AREA_LENGTH_PORT - INDENT_X, INDENT_Y, TEXT_AREA_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT, "25566");
        add(textAreaFieldPort);

        add(createButton("Back", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListenerToNotify(
                () -> new ClickChangeToPanelFromCacheGuiEvent(MainMenuGuiPanel.class))));
        add(createButton("Connect", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListenerToNotify(() -> new ClickConnectGuiEvent(
                        (!textAreaFieldIP.getTextState().getText().isEmpty()) ? textAreaFieldIP.getTextState().getText() : null,
                        textAreaFieldPort.getTextState().getText()
                ))));
    }
}
