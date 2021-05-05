package tow.game.client.menu;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.font.FontRegistry;
import tow.engine.Global;
import tow.engine.gameobject.components.Position;
import tow.engine.gui.CachedGuiPanel;
import tow.engine.services.CachedGuiElementService;

import java.util.function.Consumer;

import static tow.game.client.menu.InterfaceStyles.*;

public class MenuGuiPanel extends CachedGuiPanel {

    @Override
    public void init() {
        setStyle(createPanelStyle());
        setFocusable(false);
    }

    public void addMenuButtons(ButtonConfiguration... buttonConfigurations) {
        setSize(MENU_ELEMENT_WIDTH, buttonConfigurations.length * MENU_ELEMENT_HEIGHT);

        for (int i = 0; i < buttonConfigurations.length; i++) {
            addComponentLU(createMenuButton(buttonConfigurations[i]), 0, i * MENU_ELEMENT_HEIGHT, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);
        }
    }

    public void addButton(String text, int x, int y, int width, int height, MouseClickEventListener event){
        Button button = new Button(text);
        button.setStyle(createButtonStyle());
        button.getListenerMap().addListener(MouseClickEvent.class, event);
        addComponentLU(button, x, y, width, height);
    }

    protected Button createMenuButton(ButtonConfiguration buttonConfiguration) {
        Button button = new Button(buttonConfiguration.text);
        button.setStyle(createMenuButtonStyle());
        button.getListenerMap().addListener(MouseClickEvent.class, buttonConfiguration.event);
        button.getTextState().setFont(FontRegistry.ROBOTO_BOLD);
        button.getTextState().setFontSize(30);
        return button;
    }

    public TextAreaField createTextAreaField(int x, int y, int width, int height) {
        TextAreaField textAreaField = new TextAreaField();
        textAreaField.setStyle(createTextAreaFieldStyle());
        addComponentLU(textAreaField, x, y, width, height);
        return textAreaField;
    }

    protected static class ButtonConfiguration {

        public String text;
        public MouseClickEventListener event;

        public ButtonConfiguration(String text, MouseClickEventListener event) {
            this.text = text;
            this.event = event;
        }
    }

    public void destroy(){
        getCachedGuiElementOnActiveLocation().destroy();
    }

    //TODO Убрать зависимости от cachedGuiPanelStorage и Global.engine.render, мб от CachedGuiElementService
    public void createCachedPanel(Class<? extends CachedGuiPanel> newGuiPanelClass){
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
