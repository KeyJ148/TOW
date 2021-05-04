package tow.game.client.menu.locations;


import org.liquidengine.legui.component.Panel;
import tow.engine.image.Color;
import tow.game.client.menu.MenuLocation;

import static tow.game.client.menu.InterfaceStyles.MENU_ELEMENT_HEIGHT;
import static tow.game.client.menu.InterfaceStyles.MENU_ELEMENT_WIDTH;

public class SettingsMenuLocation extends MenuLocation {

    protected final static int SETTINGS_PANEL_WIDTH = 2*MENU_ELEMENT_WIDTH;
    protected final static int SETTINGS_PANEL_HEIGHT = 3*MENU_ELEMENT_HEIGHT;
    protected final static int INDENT_X = 10;
    protected final static int INDENT_Y = 10;
    protected final static int LENGTH_TEXT_AREA_NICK = 100;
    protected final static int BUTTON_COLOR_SIZE = 15;
    protected final static int PANEL_COLOR_WIDTH = 45;
    protected final static int PANEL_COLOR_HEIGHT = 20;

    private final static Color[] COLORS = {
        new Color(255, 255, 255),
        new Color(255, 0, 0),
        new Color(0, 255, 0),
        new Color(0, 0, 255),
        new Color(255, 255, 0),
        new Color(255, 0, 255),
        new Color(0, 255, 255),
        new Color(0, 125, 255),
        new Color(255, 125, 0),
        new Color(125, 0, 255),
        new Color(125, 125, 0),
        new Color(125, 125, 125, 125)
    };

    private Color tankColor;

    private Panel mainPanel;
/*
    public SettingsMenuLocation() {
        mainPanel = createPanel(width/2, height/2, SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT);
        addComponentToParentLU(new Label("Nickname:"), INDENT_X, INDENT_Y,
                30, 30, mainPanel);
        TextAreaField textAreaFieldNickname = createTextAreaField(INDENT_X + 60, INDENT_Y,
                LENGTH_TEXT_AREA_NICK, MENU_TEXT_FIELD_HEIGHT, mainPanel);
        SettingsConfirmationListener settingsConfirmationListener = new SettingsConfirmationListener(error -> printErrorMessage(error.getText()));

        Panel panelColor = createPanelToParent(0, 0,
                PANEL_COLOR_WIDTH, PANEL_COLOR_HEIGHT, mainPanel);
        panelColor.getStyle().getBackground().setColor(ClientData.color.getVector4f());

        for (int i = 0; i < COLORS.length; i++) {
            final int fi = i;
            Button buttonColor = createColorButton(INDENT_X + (BUTTON_COLOR_SIZE+2)*i, INDENT_Y + MENU_TEXT_FIELD_HEIGHT + 10,
                    COLORS[i], getMouseReleaseListener(event -> {
                        tankColor = COLORS[fi];
                        panelColor.getStyle().getBackground().setColor(COLORS[fi].getVector4f());
                    }), mainPanel);
        }

        Button backButton = createButton("Back to menu", INDENT_X, SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, getActivateLocationMouseReleaseListener(MainMenuLocation.class), mainPanel);
        createButton("Confirm", SETTINGS_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> settingsConfirmationListener.confirm(textAreaFieldNickname.getTextState().getText(), tankColor)), mainPanel);
    }*/

    private void printErrorMessage(String message) {
        /*addComponentToParentLU(new Label(message), INDENT_X, MAIN_PANEL_HEIGHT - INDENT_Y*2 - BUTTON_HEIGHT,
                30, 30, mainPanel);*//*
        addComponentToParentLU(new Label(message), INDENT_X, INDENT_Y,
                30, 30, mainPanel);*/
    }
/*
    private Button createColorButton(int x, int y, Color color, MouseClickEventListener event, Component parent){
        Button button = new Button("");
        Background background = new Background();
        background.setColor(color.getVector4f());
        button.getStyle().setBackground(background);
        button.getStyle().setBorder(createButtonBorder());
        button.getListenerMap().addListener(MouseClickEvent.class, event);
        addComponentToParentLU(button, x, y, BUTTON_COLOR_SIZE, BUTTON_COLOR_SIZE, parent);
        return button;
    }*/
}
