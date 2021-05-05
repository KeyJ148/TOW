package cc.abro.tow.client.menu;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gui.CachedGuiPanel;
import cc.abro.orchengine.services.CachedGuiElementService;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.font.FontRegistry;

import java.util.function.Consumer;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class MenuGuiPanel extends CachedGuiPanel {

    @Override
    public void init() {
        setStyle(createPanelStyle());
        setFocusable(false);
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

    public void addButton(String text, int x, int y, int width, int height, MouseClickEventListener event) {
        Button button = new Button(text);
        button.setStyle(createButtonStyle());
        button.getListenerMap().addListener(MouseClickEvent.class, event);
        button.getTextState().setFont(FontRegistry.ROBOTO_REGULAR);
        button.getTextState().setFontSize(BUTTON_FONT_SIZE);
        button.getHoveredStyle().setBackground(createHoveredButtonBackground());
        button.getPressedStyle().setBackground(createPressedButtonBackground());
        addComponent(button, x, y, width, height);
    }

    protected Button createMenuButton(ButtonConfiguration buttonConfiguration) {
        Button button = new Button(buttonConfiguration.text);
        button.setStyle(createMenuButtonStyle());
        button.getListenerMap().addListener(MouseClickEvent.class, buttonConfiguration.event);
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

    protected static class ButtonConfiguration {

        public String text;
        public MouseClickEventListener event;

        public ButtonConfiguration(String text, MouseClickEventListener event) {
            this.text = text;
            this.event = event;
        }
    }

    public void destroy() {
        getCachedGuiElementOnActiveLocation().destroy();
    }

    //TODO Убрать зависимости от cachedGuiPanelStorage и Global.engine.render, мб от CachedGuiElementService
    public void createCachedPanel(Class<? extends CachedGuiPanel> newGuiPanelClass) {
        CachedGuiPanel cachedGuiPanel = Global.cachedGuiPanelStorage.getPanel(newGuiPanelClass);
        new CachedGuiElementService().addCachedComponentToLocationShiftedToCenter(cachedGuiPanel,
                Global.engine.render.getWidth() / 2,
                Global.engine.render.getHeight() / 2,
                getCachedGuiElementOnActiveLocation().getGameObject().getComponent(Position.class).location);
    }

    public MouseClickEventListener getDestroyThisPanelMouseReleaseListener() {
        return getMouseReleaseListener(event -> destroy());
    }

    public MouseClickEventListener getCreateCachedPanelMouseReleaseListener(Class<? extends CachedGuiPanel> newGuiPanelClass) {
        return getMouseReleaseListener(event -> createCachedPanel(newGuiPanelClass));
    }

    public MouseClickEventListener getChangeCachedPanelMouseReleaseListener(Class<? extends CachedGuiPanel> newGuiPanelClass) {
        return getMouseReleaseListener(event -> {
            destroy();
            createCachedPanel(newGuiPanelClass);
        });
    }

    @SuppressWarnings("unchecked")
    public MouseClickEventListener getMouseReleaseListener(Consumer<MouseClickEvent<Component>> mouseReleaseAction) {
        return event -> {
            event.getTargetComponent().setFocused(false);
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                mouseReleaseAction.accept(event);
            }
        };
    }
}
