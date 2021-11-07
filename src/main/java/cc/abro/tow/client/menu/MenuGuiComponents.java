package cc.abro.tow.client.menu;

import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gui.EventableGuiPanel;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.font.FontRegistry;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.InterfaceStyles.createScrollablePanelContainerStyle;

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

    public static Panel createMenuPanel(ButtonConfiguration... buttonConfigurations) {
        final int INDENT = 5;
        final int MENU_PANEL_WIDTH = INDENT*2 + MENU_BUTTON_WIDTH;
        final int MENU_PANEL_HEIGHT = INDENT + buttonConfigurations.length * (INDENT + MENU_BUTTON_HEIGHT);

        Panel menu = new Panel();
        menu.setSize(MENU_PANEL_WIDTH, MENU_PANEL_HEIGHT);
        menu.setStyle(createInvisibleStyle());
        for (int i = 0; i < buttonConfigurations.length; i++) {
            menu.add(createMenuButton(buttonConfigurations[i],
                    (MENU_PANEL_WIDTH - MENU_BUTTON_WIDTH)/2,
                    (MENU_PANEL_HEIGHT - (buttonConfigurations.length * (INDENT + MENU_BUTTON_HEIGHT) - INDENT))/2 + i * (INDENT + MENU_BUTTON_HEIGHT),
                    MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
        }
        return menu;
    }

    private static Button createMenuButton(ButtonConfiguration buttonConfiguration, int x, int y, int width, int height) {
        Button button = new Button(buttonConfiguration.text);
        button.setStyle(createMenuButtonStyle());
        //button.getListenerMap().addListener(MouseClickEvent.class, getMouseReleaseListenerToNotify(buttonConfiguration.event));
        button.getHoveredStyle().setBackground(createHoveredMenuButtonBackground());
        button.getPressedStyle().setBackground(createPressedMenuButtonBackground());
        button.getHoveredStyle().setBorder(createButtonBorder());
        button.getPressedStyle().setBorder(createButtonBorder());
        button.setPosition(x, y);
        button.setSize(width, height);
        return button;
    }

    public static Button createButton(String text, int x, int y, int width, int height, Supplier<GuiElementEvent> eventSupplier) {
        return createButtonWithEvents(text, x, y, width, height, () -> List.of(eventSupplier.get()));
    }

    public static Button createButtonWithEvents(String text, int x, int y, int width, int height,
                                         Supplier<List<GuiElementEvent>> eventSupplier) {
        Button button = new Button(text);
        button.setStyle(createButtonStyle());
        //button.getListenerMap().addListener(MouseClickEvent.class, getMouseReleaseListenerToNotifyEvents(eventSupplier));
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

    public static class ButtonConfiguration {

        public String text;
        public GuiElementEvent event;

        public ButtonConfiguration(String text, GuiElementEvent event) {
            this.text = text;
            this.event = event;
        }
    }
}
