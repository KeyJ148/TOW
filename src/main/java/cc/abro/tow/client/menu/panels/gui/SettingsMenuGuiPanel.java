package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;
import cc.abro.orchengine.image.Color;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.menu.panels.controllers.settings.ClickConfirmController;
import cc.abro.tow.client.menu.panels.events.settings.ClickConfirmGuiEvent;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.Background;

import java.util.Set;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class SettingsMenuGuiPanel extends MenuGuiPanel {

    protected final static int SETTINGS_PANEL_WIDTH = 2 * MENU_ELEMENT_WIDTH;
    protected final static int SETTINGS_PANEL_HEIGHT = 3 * MENU_ELEMENT_HEIGHT;
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
    };

    private Color tankColor;

    public SettingsMenuGuiPanel() {
        super(() -> Set.of(new ClickChangePanelController(), new ClickConfirmController()));
        setSize(SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT);

        addLabel("Nickname:", INDENT_X, INDENT_Y, 30, MENU_TEXT_FIELD_HEIGHT);
        TextAreaField textAreaFieldNickname =
                createTextAreaField(INDENT_X + LABEL_LENGTH_NICKNAME, INDENT_Y, LENGTH_TEXT_AREA_NICK, MENU_TEXT_FIELD_HEIGHT);

        Panel panelColor =
                createPanel(SETTINGS_PANEL_WIDTH - PANEL_COLOR_WIDTH, 0, PANEL_COLOR_WIDTH, PANEL_COLOR_HEIGHT);
        panelColor.getStyle().getBackground().setColor(ClientData.color.getVector4f());

        for (int i = 0; i < COLORS.length; i++) {
            final int fi = i;
            Button buttonColor = createColorButton(INDENT_X + (BUTTON_COLOR_SIZE + 2) * i, INDENT_Y + MENU_TEXT_FIELD_HEIGHT + 10, COLORS[i],
                    getMouseReleaseListener(event -> {
                        tankColor = COLORS[fi];
                        panelColor.getStyle().getBackground().setColor(COLORS[fi].getVector4f());
                    }));
        }

        addButton("Back to menu", INDENT_X, SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickChangePanelGuiEvent(MainMenuGuiPanel.class));
        addButton("Confirm", SETTINGS_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                () -> new ClickConfirmGuiEvent(textAreaFieldNickname.getTextState().getText(), tankColor));
    }

    private void printErrorMessage(String message) {
        add(new Label(message, INDENT_X, SETTINGS_PANEL_HEIGHT - INDENT_Y * 2 - BUTTON_HEIGHT, 30, 30));
    }

    private Button createColorButton(int x, int y, Color color, MouseClickEventListener event) {
        Button button = new Button("");
        Background background = new Background();
        background.setColor(color.getVector4f());
        button.getStyle().setBackground(background);
        button.getStyle().setBorder(createButtonBorder());
        button.getListenerMap().addListener(MouseClickEvent.class, event);
        button.setSize(BUTTON_COLOR_SIZE, BUTTON_COLOR_SIZE);
        button.setPosition(x, y);
        add(button);
        return button;
    }
}
