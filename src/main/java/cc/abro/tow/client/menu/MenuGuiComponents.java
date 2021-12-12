package cc.abro.tow.client.menu;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.font.FontRegistry;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public final class MenuGuiComponents {

    public static Label createBigLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.setStyle(createBigLabelStyle());
        return label;
    }

    public static Label createLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.setStyle(createLabelStyle());
        return label;
    }

    public static Panel createMenuPanel(MenuButtonConfiguration... buttonConfigurations) {
        int countButtons = buttonConfigurations.length;

        final int INDENT = 5;
        final int MENU_PANEL_WIDTH = INDENT*2 + MENU_BUTTON_WIDTH;
        final int MENU_PANEL_HEIGHT = INDENT + countButtons * (INDENT + MENU_BUTTON_HEIGHT);

        Panel menu = new Panel();
        menu.setSize(MENU_PANEL_WIDTH, MENU_PANEL_HEIGHT);
        menu.setStyle(createInvisibleStyle());
        for (int i = 0; i < countButtons; i++) {
            Button button = createMenuButton(buttonConfigurations[i].text,
                    (MENU_PANEL_WIDTH - MENU_BUTTON_WIDTH)/2,
                    (MENU_PANEL_HEIGHT - (countButtons * (INDENT + MENU_BUTTON_HEIGHT) - INDENT))/2 + i * (INDENT + MENU_BUTTON_HEIGHT),
                    MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT,
                    buttonConfigurations[i].eventListener);
            menu.add(button);
        }
        return menu;
    }

    public static Button createMenuButton(String text, int x, int y, int width, int height,
                                          MouseClickEventListener mouseClickEventListener) {
        Button button = new Button(text);
        button.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        button.setStyle(createMenuButtonStyle());
        button.getHoveredStyle().setBackground(createHoveredMenuButtonBackground());
        button.getPressedStyle().setBackground(createPressedMenuButtonBackground());
        button.getHoveredStyle().setBorder(createButtonBorder());
        button.getPressedStyle().setBorder(createButtonBorder());
        button.setPosition(x, y);
        button.setSize(width, height);
        return button;
    }

    public static Button createButton(String text, int x, int y, int width, int height,
                                      MouseClickEventListener mouseClickEventListener) {
        Button button = new Button(text);
        button.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        button.setStyle(createButtonStyle());
        button.getStyle().setFont(FontRegistry.ROBOTO_REGULAR);
        button.getStyle().setFontSize(BUTTON_FONT_SIZE);
        button.getStyle().setTextColor(BLACK_COLOR);
        button.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        button.getHoveredStyle().setBackground(createHoveredButtonBackground());
        button.getPressedStyle().setBackground(createPressedButtonBackground());
        button.setPosition(x, y);
        button.setSize(width, height);
        return button;
    }

    public static TextAreaField createTextAreaField(int x, int y, int width, int height) {
        TextAreaField textAreaField = new TextAreaField();
        textAreaField.setStyle(createTextAreaFieldStyle());
        textAreaField.getFocusedStyle().setBackground(createFocusedTextAreaFieldBackground());
        textAreaField.getFocusedStyle().setBorder(createTextAreaFieldBorder());
        textAreaField.getStyle().setFont(FontRegistry.ROBOTO_REGULAR);
        textAreaField.getStyle().setFontSize(LABEL_FONT_SIZE);
        textAreaField.getStyle().setTextColor(BLACK_COLOR);
        textAreaField.setPosition(x, y);
        textAreaField.setSize(width, height);
        return textAreaField;
    }

    public static TextAreaField createTextAreaField(int x, int y, int width, int height, String text) {
        TextAreaField textAreaField = createTextAreaField(x, y, width, height);
        textAreaField.getTextState().setText(text);
        return textAreaField;
    }

    public static TextAreaField createTextAreaFieldWithANumber(int x, int y, int width, int height, String text) {
        TextAreaField textAreaField = createTextAreaField(x, y, width, height);
        textAreaField.getTextState().setText(text);
        textAreaField.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        textAreaField.getStyle().setTextColor(BLACK_COLOR);
        return textAreaField;
    }

    public static Panel createPanel(int width, int height) {
        Panel panel = new Panel();
        panel.setStyle(createPanelStyle());
        panel.setFocusable(false);
        panel.setSize(width, height);
        return panel;
    }


    public static LabelGuiPanel createLabelPanel(String message, int width, int height) {
        Panel panel = createPanel(width, height);
        int widthOfMessage = (int) (LABEL_FONT_SIZE*9/24) * message.length();
        Label label = createLabel(message, (width - widthOfMessage)/2, BLOCKING_BUTTON_INDENT_Y,
                widthOfMessage, MENU_TEXT_FIELD_HEIGHT);
        panel.add(label);
        return new LabelGuiPanel(panel, label);
    }

    public static ButtonGuiPanel createButtonPanel(String labelText, String buttonText, MouseClickEventListener eventListener) {
        LabelGuiPanel labelGuiPanel = createLabelPanel(labelText,
                BLOCKING_BUTTON_ELEMENT_WIDTH, BLOCKING_BUTTON_ELEMENT_HEIGHT);
        Button button = createButton(buttonText,
                (int) (labelGuiPanel.panel.getSize().x - SMALL_BUTTON_WIDTH) / 2,
                (int) labelGuiPanel.panel.getSize().y - BUTTON_HEIGHT - BLOCKING_BUTTON_INDENT_Y,
                SMALL_BUTTON_WIDTH, BUTTON_HEIGHT, eventListener);
        labelGuiPanel.panel.add(button);
        return new ButtonGuiPanel(labelGuiPanel.panel, labelGuiPanel.label, button);
    }

    public static ScrollablePanel createScrollablePanel(int x, int y, int width, int height) {
        ScrollablePanel panel = new ScrollablePanel(x, y, width, height);
        panel.setStyle(createScrollablePanelStyle());
        ScrollBar scrollBar = panel.getVerticalScrollBar();
        scrollBar.setStyle(createScrollBarStyle());
        scrollBar.setScrollColor(DARK_GRAY_COLOR);
        panel.getContainer().setStyle(createScrollablePanelContainerStyle());
        panel.getContainer().setSize(panel.getSize());
        panel.setHorizontalScrollBarVisible(false);
        panel.setFocusable(false);
        return panel;
    }

    public static record MenuButtonConfiguration(String text, MouseClickEventListener eventListener) { }
    public static record LabelGuiPanel(Panel panel, Label label) { }
    public static record ButtonGuiPanel(Panel panel, Label label, Button button) { }
}
