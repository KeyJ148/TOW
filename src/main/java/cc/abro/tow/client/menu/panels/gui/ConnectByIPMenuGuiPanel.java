package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;
import cc.abro.tow.client.menu.panels.controllers.connectbyip.ClickConnectController;
import cc.abro.tow.client.menu.panels.events.connectbyip.ClickConnectGuiEvent;
import org.liquidengine.legui.component.TextAreaField;

import java.util.Set;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class ConnectByIPMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 4 * MENU_ELEMENT_WIDTH / 3;
    protected final static int MAIN_PANEL_HEIGHT = 5 * MENU_ELEMENT_HEIGHT / 3;

    public ConnectByIPMenuGuiPanel() {
        init(() -> Set.of(new ClickChangePanelController(), new ClickConnectController()));
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        addLabel("IP:", INDENT_X, INDENT_Y, LABEL_LENGTH_ID, MENU_TEXT_FIELD_HEIGHT);
        TextAreaField textAreaFieldIP =
                createTextAreaField(INDENT_X + LABEL_LENGTH_ID, INDENT_Y, TEXT_AREA_LENGTH_IP, MENU_TEXT_FIELD_HEIGHT);

        addLabel("Port:", MAIN_PANEL_WIDTH - LABEL_LENGTH_PORT - TEXT_AREA_LENGTH_PORT - INDENT_X, INDENT_Y, 30, MENU_TEXT_FIELD_HEIGHT);
        TextAreaField textAreaFieldPort =
                createTextAreaField(MAIN_PANEL_WIDTH - TEXT_AREA_LENGTH_PORT - INDENT_X, INDENT_Y, TEXT_AREA_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT, "25566");

        addButton("Back", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                () -> new ClickChangePanelGuiEvent(ConnectMenuGuiPanel.class));
        addButton("Connect", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                () -> new ClickConnectGuiEvent(
                        (!textAreaFieldIP.getTextState().getText().isEmpty()) ? textAreaFieldIP.getTextState().getText() : null,
                        textAreaFieldPort.getTextState().getText()
                ));
    }
}
