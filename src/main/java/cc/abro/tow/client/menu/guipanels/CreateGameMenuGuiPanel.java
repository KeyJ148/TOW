package cc.abro.tow.client.menu.guipanels;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gui.CachedGuiPanel;
import cc.abro.orchengine.services.CachedGuiElementService;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.menu.HostingListener;
import cc.abro.tow.client.menu.MenuGuiPanel;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class CreateGameMenuGuiPanel extends MenuGuiPanel {

    protected final static int MAIN_PANEL_WIDTH = 4 * MENU_ELEMENT_WIDTH / 3;
    protected final static int MAIN_PANEL_HEIGHT = 10 * MENU_ELEMENT_HEIGHT / 3;

    public CreateGameMenuGuiPanel() {
        init();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        addLabel("Port:", INDENT_X, INDENT_Y, 30, MENU_TEXT_FIELD_HEIGHT);
        TextAreaField textAreaFieldPort = createTextAreaField(INDENT_X + LABEL_LENGTH_PORT, INDENT_Y,
                LENGTH_TEXT_AREA_PORT, MENU_TEXT_FIELD_HEIGHT, "25566");

        HostingListener hostingListener = new HostingListener(error -> new PrintErrorGuiPanel(error.getText(), this));
        addButton("Back to menu", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, getChangeCachedPanelMouseReleaseListener(MainMenuGuiPanel.class));
        addButton("Create the game", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_X,
                BUTTON_WIDTH, BUTTON_HEIGHT, getMouseReleaseListener(event -> hostingListener.host(textAreaFieldPort.getTextState().getText())));
    }
}
