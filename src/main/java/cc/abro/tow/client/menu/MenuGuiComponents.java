package cc.abro.tow.client.menu;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.style.font.FontRegistry;

import java.util.ArrayList;
import java.util.List;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public final class MenuGuiComponents {

    public static Label createBigLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.setStyle(createBigLabelStyle());
        return label;
    }

    public static Label createLabel (String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.setStyle(createLabelStyle());
        return label;
    }

    public static Pair<Panel, List<Button>> createMenuPanel(List<String> buttonTexts) {
        int countButtons = buttonTexts.size();

        final int INDENT = 5;
        final int MENU_PANEL_WIDTH = INDENT*2 + MENU_BUTTON_WIDTH;
        final int MENU_PANEL_HEIGHT = INDENT + countButtons * (INDENT + MENU_BUTTON_HEIGHT);

        Panel menu = new Panel();
        menu.setSize(MENU_PANEL_WIDTH, MENU_PANEL_HEIGHT);
        menu.setStyle(createInvisibleStyle());
        List<Button> buttons = new ArrayList<>(countButtons);
        for (int i = 0; i < countButtons; i++) {
            Button button = createMenuButton(buttonTexts.get(i),
                    (MENU_PANEL_WIDTH - MENU_BUTTON_WIDTH)/2,
                    (MENU_PANEL_HEIGHT - (countButtons * (INDENT + MENU_BUTTON_HEIGHT) - INDENT))/2 + i * (INDENT + MENU_BUTTON_HEIGHT),
                    MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
            buttons.add(button);
            menu.add(button);
        }
        return new ImmutablePair<>(menu, buttons);
    }

    public static Button createMenuButton(String text, int x, int y, int width, int height) {
        Button button = new Button(text);
        button.setStyle(createMenuButtonStyle());
        button.getHoveredStyle().setBackground(createHoveredMenuButtonBackground());
        button.getPressedStyle().setBackground(createPressedMenuButtonBackground());
        button.getHoveredStyle().setBorder(createButtonBorder());
        button.getPressedStyle().setBorder(createButtonBorder());
        button.setPosition(x, y);
        button.setSize(width, height);
        return button;
    }

    public static Button createButton(String text, int x, int y, int width, int height) {
        Button button = new Button(text);
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

    public Panel createPanel(int x, int y, int width, int height) {
        Panel panel = new Panel();
        panel.setStyle(createPanelStyle());
        panel.setFocusable(false);
        panel.setPosition(x, y);
        panel.setSize(width, height);
        return panel;
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
}
