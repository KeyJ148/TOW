package tow.game.client.menu.guipanels;

import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.TextAreaField;
import tow.game.client.menu.HostingListener;
import tow.game.client.menu.MenuGuiPanel;

import static tow.game.client.menu.InterfaceStyles.*;

public class CreateGameMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 4*MENU_ELEMENT_WIDTH/3;
    protected final static int MAIN_PANEL_HEIGHT = 10*MENU_ELEMENT_HEIGHT/3;
    protected final static int INDENT_X = 10;
    protected final static int INDENT_Y = 10;
    protected final static int LENGTH_TEXT_AREA_PORT = 40;

    public CreateGameMenuGuiPanel() {
        init();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        add(new Label("Port:", INDENT_X, INDENT_Y,30, 30));
        TextAreaField textAreaFieldPort = createTextAreaField(INDENT_X + 30, INDENT_Y,
                LENGTH_TEXT_AREA_PORT, MENU_TEXT_FIELD_HEIGHT);
        textAreaFieldPort.getTextState().setText("25566");
        HostingListener hostingListener = new HostingListener(error -> printErrorMessage(error.getText()));
        addButton("Back to menu", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X,
                BUTTON_WIDTH, BUTTON_HEIGHT, getChangeCachedPanelMouseReleaseListener(MainMenuGuiPanel.class));
        addButton("Create the game", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X,
                BUTTON_WIDTH, BUTTON_HEIGHT, getMouseReleaseListener(event -> hostingListener.host(textAreaFieldPort.getTextState().getText())));
    }

    private void printErrorMessage(String message) {
        add(new Label(message, INDENT_X, MAIN_PANEL_HEIGHT - INDENT_Y*2 - BUTTON_HEIGHT,
                30, 30));
        add(new Label(message, INDENT_X, INDENT_Y,
                30, 30));
    }
}
