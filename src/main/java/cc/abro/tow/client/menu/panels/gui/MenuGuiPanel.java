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

    public void init() {
        setStyle(createPanelStyle());
        setFocusable(false);
    }

    public void init(Supplier<Set<GuiElementController>> controllersSupplier){
        init();
        Manager.getService(PanelControllersStorage.class).registry(getClass(), controllersSupplier);
    }

    public void addLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.getTextState().setFont(FontRegistry.ROBOTO_REGULAR);
        label.getTextState().setFontSize(LABEL_FONT_SIZE);
        add(label);
    }

    public void addMenuButtons(ButtonConfiguration... buttonConfigurations) {
        setSize(MENU_ELEMENT_WIDTH, buttonConfigurations.length * MENU_ELEMENT_HEIGHT);
        for (int i = 0; i < buttonConfigurations.length; i++) {
            addComponent(createMenuButton(buttonConfigurations[i]), 0, i * MENU_ELEMENT_HEIGHT, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);
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
        add(panel);
        return panel;
    }

    public ScrollablePanel createScrollablePanel(int x, int y, int width, int height) {
        ScrollablePanel panel = new ScrollablePanel(x, y, width, height);
        /*Style style = createScrollablePanelStyle();
        panel.getStyle().setBackground(style.getBackground());
        panel.getStyle().setBorder(style.getBorder());*/
        panel.setStyle(createScrollablePanelStyle());
        panel.setHorizontalScrollBarVisible(false);
        panel.setFocusable(false);
        panel.getContainer().setSize(panel.getSize().x, 10000);
        panel.getContainer().add(new Button(0, 0, 200, 200));
        panel.getContainer().add(new Button(000, 200, 200, 200));
        panel.getContainer().add(new Button(000, 400, 200, 200));
        panel.getContainer().add(new Button(000, 600, 200, 200));
        panel.getContainer().add(new Button(000, 800, 200, 200));

        /*ScrollBar scrollBar1 = new ScrollBar(360, 170, 20, 100, 20);
        scrollBar1.setOrientation(Orientation.VERTICAL);
        scrollBar1.setVisibleAmount(20);
        scrollBar1.setArrowsEnabled(true);
        scrollBar1.getStyle().getBackground().setColor(ColorConstants.white());
        scrollBar1.setScrollColor(ColorConstants.darkGray());
        scrollBar1.setArrowColor(ColorConstants.darkGray());
        scrollBar1.getStyle().setBorder(new SimpleLineBorder(ColorConstants.red(), 1));
        add(scrollBar1);*/
        addComponent(panel, x, y, width, height);
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
