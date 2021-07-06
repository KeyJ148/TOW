package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.controllers.ClickChangePanelController;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickCreateController;
import cc.abro.tow.client.menu.panels.events.ClickChangePanelGuiEvent;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;

import java.util.Set;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class CreateGameMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 4 * MENU_ELEMENT_WIDTH / 3;
    protected final static int MAIN_PANEL_HEIGHT = 10 * MENU_ELEMENT_HEIGHT / 3;
    protected final static int INDENT_Y_LAYER2 = + INDENT_Y + MENU_TEXT_FIELD_HEIGHT + 20;

    public CreateGameMenuGuiPanel() {
        init(() -> Set.of(new ClickChangePanelController(), new ClickCreateController()));
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        addLabel("Server name:", INDENT_X, INDENT_Y, LABEL_LENGTH_SERVER_NAME, MENU_TEXT_FIELD_HEIGHT);
        TextAreaField textAreaFieldServerName = createTextAreaField(INDENT_X + LABEL_LENGTH_SERVER_NAME, INDENT_Y,
                TEXT_AREA_LENGTH_SERVER_NAME, MENU_TEXT_FIELD_HEIGHT, "Server");
        addLabel("Port:", INDENT_X, INDENT_Y_LAYER2, LABEL_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT);
        TextAreaField textAreaFieldPort = createTextAreaField(INDENT_X + LABEL_LENGTH_PORT, INDENT_Y_LAYER2,
                TEXT_AREA_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT, "25566");
        addLabel("Maximum people:", INDENT_X + LABEL_LENGTH_PORT + TEXT_AREA_LENGTH_PORT + 10, INDENT_Y_LAYER2,
                LABEL_LENGTH_MAX_PEOPLE, MENU_TEXT_FIELD_HEIGHT);
        TextAreaField textAreaFieldMaxPeople = createTextAreaField(INDENT_X + LABEL_LENGTH_PORT + TEXT_AREA_LENGTH_PORT + LABEL_LENGTH_MAX_PEOPLE + 10, INDENT_Y_LAYER2,
                TEXT_AREA_LENGTH_MAX_PEOPLE, MENU_TEXT_FIELD_HEIGHT, "1");
        textAreaFieldMaxPeople.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);


        addButton("Back to menu", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickChangePanelGuiEvent(MainMenuGuiPanel.class));
        addButton("Create a game", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickCreateGuiEvent(
                        textAreaFieldPort.getTextState().getText(),
                        Integer.parseInt(textAreaFieldMaxPeople.getTextState().getText())
                ));
    }
}
