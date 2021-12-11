package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangeToPanelFromCacheGuiEvent;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import org.liquidengine.legui.component.TextAreaField;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;
import static cc.abro.tow.client.menu.MenuGuiService.createButton;

public class CreateGameMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 4 * MENU_ELEMENT_WIDTH / 3;
    protected final static int MAIN_PANEL_HEIGHT = 10 * MENU_ELEMENT_HEIGHT / 3;
    protected final static int INDENT_Y_LAYER2 = + INDENT_Y + MENU_TEXT_FIELD_HEIGHT + 20;

    public CreateGameMenuGuiPanel() {
        super();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        add(createLabel("Server name:", INDENT_X, INDENT_Y, LABEL_LENGTH_SERVER_NAME, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldServerName = createTextAreaField(INDENT_X + LABEL_LENGTH_SERVER_NAME, INDENT_Y,
                TEXT_AREA_LENGTH_SERVER_NAME, MENU_TEXT_FIELD_HEIGHT, "Server");
        add(textAreaFieldServerName);

        add(createLabel("Port:", INDENT_X, INDENT_Y_LAYER2, LABEL_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldPort = createTextAreaField(INDENT_X + LABEL_LENGTH_PORT, INDENT_Y_LAYER2,
                TEXT_AREA_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT, "25566");
        add(textAreaFieldPort);

        add(createLabel("Maximum people:", INDENT_X + LABEL_LENGTH_PORT + TEXT_AREA_LENGTH_PORT + 10, INDENT_Y_LAYER2,
                LABEL_LENGTH_MAX_PEOPLE, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldMaxPeople = createTextAreaFieldWithANumber(INDENT_X + LABEL_LENGTH_PORT + TEXT_AREA_LENGTH_PORT + LABEL_LENGTH_MAX_PEOPLE + 10, INDENT_Y_LAYER2,
                TEXT_AREA_LENGTH_MAX_PEOPLE, MENU_TEXT_FIELD_HEIGHT, "1");
        add(textAreaFieldMaxPeople);

        add(createButton("Back to menu", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(MainMenuGuiPanel.class))));
        add(createButton("Create a game", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, getMouseReleaseListenerToNotify(() -> new ClickCreateGuiEvent(
                        textAreaFieldPort.getTextState().getText(),
                        Integer.parseInt(textAreaFieldMaxPeople.getTextState().getText())
                ))));
    }
}
