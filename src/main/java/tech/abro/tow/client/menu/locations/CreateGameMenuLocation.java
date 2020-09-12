package tech.abro.tow.client.menu.locations;

import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import tech.abro.tow.client.menu.HostingListener;
import tech.abro.tow.client.menu.MenuLocation;

import static tech.abro.tow.client.menu.InterfaceStyles.*;

public class CreateGameMenuLocation extends MenuLocation {

    protected final static int MAIN_PANEL_WIDTH = 4*MENU_ELEMENT_WIDTH/3;
    protected final static int MAIN_PANEL_HEIGHT = 10*MENU_ELEMENT_HEIGHT/3;
    protected final static int INDENT_X = 10;
    protected final static int INDENT_Y = 10;
    protected final static int LENGTH_TEXT_AREA_PORT = 40;

    private Panel mainPanel;

    public CreateGameMenuLocation() {

        mainPanel = createPanel(width/2,height/2, MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
        addComponentToParentLU(new Label("Port"), INDENT_X, INDENT_Y,
                30, 30, mainPanel);
        TextAreaField textAreaFieldPort = createTextAreaField(INDENT_X + 30, INDENT_Y,
                LENGTH_TEXT_AREA_PORT, MENU_TEXT_FIELD_HEIGHT, mainPanel);
        textAreaFieldPort.getTextState().setText("25566");
        HostingListener hostingListener = new HostingListener(error -> printErrorMessage(error.getText()));
        createButton("Back to menu", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X,
                BUTTON_WIDTH, BUTTON_HEIGHT, getActivateLocationMouseReleaseListener(MainMenuLocation.class), mainPanel);
        createButton("Create the game", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X,
                BUTTON_WIDTH, BUTTON_HEIGHT, getMouseReleaseListener(event -> hostingListener.host(textAreaFieldPort.getTextState().getText())), mainPanel);
    }

    private void printErrorMessage(String message) {
        /*addComponentToParentLU(new Label(message), INDENT_X, MAIN_PANEL_HEIGHT - INDENT_Y*2 - BUTTON_HEIGHT,
                30, 30, mainPanel);*/
        addComponentToParentLU(new Label(message), INDENT_X, INDENT_Y,
                30, 30, mainPanel);
    }
}
