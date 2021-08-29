package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.orchengine.gui.PanelControllersStorage;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.style.font.FontRegistry;

import java.util.Set;
import java.util.function.Supplier;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class MenuGuiPanel extends EventableGuiPanel {

    public MenuGuiPanel() {
        setStyle(createPanelStyle());
        setFocusable(false);
    }

    /**
     * Used only for cached panels
     * @param controllersSupplier List of controllers, which will cached
     */
    public MenuGuiPanel(Supplier<Set<GuiElementController>> controllersSupplier) {
        this();
        Manager.getService(PanelControllersStorage.class).registry(getClass(), controllersSupplier);
    }

    public Label addLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.getTextState().setFont(FontRegistry.ROBOTO_REGULAR);
        label.getTextState().setFontSize(LABEL_FONT_SIZE);
        add(label);
        return label;
    }

    public void addBigLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.getTextState().setFont(FontRegistry.ROBOTO_BOLD);
        label.getTextState().setFontSize(BIG_LABEL_FONT_SIZE);
        add(label);
    }

    public Label createLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.getTextState().setFont(FontRegistry.ROBOTO_BOLD);
        label.getTextState().setFontSize(BIG_LABEL_FONT_SIZE);
        return label;
    }

    public void addMenuButtons(ButtonConfiguration... buttonConfigurations) {
        final int INDENT = 5;
        final int MENU_PANEL_WIDTH = INDENT*2 + MENU_BUTTON_WIDTH;
        final int MENU_PANEL_HEIGHT = INDENT + buttonConfigurations.length * (INDENT + MENU_BUTTON_HEIGHT);
        setSize(MENU_PANEL_WIDTH, MENU_PANEL_HEIGHT);
        setStyle(createInvisibleStyle());
        for (int i = 0; i < buttonConfigurations.length; i++) {
            addComponent(createMenuButton(buttonConfigurations[i]),
                    (MENU_PANEL_WIDTH - MENU_BUTTON_WIDTH)/2,
                    (MENU_PANEL_HEIGHT - (buttonConfigurations.length * (INDENT + MENU_BUTTON_HEIGHT) - INDENT))/2 + i * (INDENT + MENU_BUTTON_HEIGHT),
                    MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        }
    }

    public void addButton(String text, int x, int y, int width, int height, Supplier<GuiElementEvent> eventSupplier) {
        Button button = new Button(text);
        button.setStyle(createButtonStyle());
        button.getListenerMap().addListener(MouseClickEvent.class, getMouseReleaseListenerToNotify(eventSupplier));
        button.getTextState().setFont(FontRegistry.ROBOTO_REGULAR);
        button.getTextState().setFontSize(BUTTON_FONT_SIZE);
        button.getHoveredStyle().setBackground(createHoveredButtonBackground());
        button.getPressedStyle().setBackground(createPressedButtonBackground());
        addComponent(button, x, y, width, height);
    }

    protected Button createMenuButton(ButtonConfiguration buttonConfiguration) {
        Button button = new Button(buttonConfiguration.text);
        button.setStyle(createMenuButtonStyle());
        button.getListenerMap().addListener(MouseClickEvent.class, getMouseReleaseListenerToNotify(buttonConfiguration.event));
        button.getTextState().setFont(FontRegistry.ROBOTO_BOLD);
        button.getTextState().setFontSize(MENU_BUTTON_FONT_SIZE);
        button.getHoveredStyle().setBackground(createHoveredMenuButtonBackground());
        button.getPressedStyle().setBackground(createPressedMenuButtonBackground());
        button.getHoveredStyle().setBorder(createButtonBorder());
        button.getPressedStyle().setBorder(createButtonBorder());
        return button;
    }

    public TextAreaField createTextAreaField(int x, int y, int width, int height) {
        TextAreaField textAreaField = new TextAreaField();
        textAreaField.setStyle(createTextAreaFieldStyle());
        textAreaField.getFocusedStyle().setBackground(createFocusedTextAreaFieldBackground());
        textAreaField.getFocusedStyle().setBorder(createTextAreaFieldBorder());
        textAreaField.getTextState().setFont(FontRegistry.ROBOTO_REGULAR);
        textAreaField.getTextState().setFontSize(LABEL_FONT_SIZE);
        addComponent(textAreaField, x, y, width, height);
        return textAreaField;
    }

    public TextAreaField createTextAreaField(int x, int y, int width, int height, String text) {
        TextAreaField textAreaField = createTextAreaField(x, y, width, height);
        textAreaField.getTextState().setText(text);
        return textAreaField;
    }

    public Panel createPanel(int x, int y, int width, int height) {
        Panel panel = new Panel();
        panel.setStyle(createPanelStyle());
        panel.setFocusable(false);
        addComponent(panel, x, y, width, height);
        return panel;
    }

    public ScrollablePanel createScrollablePanel(int x, int y, int width, int height) {
        ScrollablePanel panel = new ScrollablePanel(x, y, width, height);
        panel.setStyle(createScrollablePanelStyle());
        ScrollBar scrollBar = panel.getVerticalScrollBar();
        scrollBar.setStyle(createScrollBarStyle());
        scrollBar.setScrollColor(DARK_GRAY_COLOR);
        panel.getContainer().setStyle(createScrollablePanelContainerStyle());
        panel.getContainer().setSize(panel.getSize());
        panel.setHorizontalScrollBarVisible(false);
        panel.setFocusable(false);
        add(panel);
        return panel;
    }

    protected static class ButtonConfiguration {

        public String text;
        public GuiElementEvent event;

        public ButtonConfiguration(String text, GuiElementEvent event) {
            this.text = text;
            this.event = event;
        }
    }
}
