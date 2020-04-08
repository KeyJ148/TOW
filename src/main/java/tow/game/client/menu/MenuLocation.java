package tow.game.client.menu;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.font.FontRegistry;
import tow.engine.Global;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.GameObjectFactory;
import tow.engine.gameobject.components.render.GuiRender;
import tow.engine.image.Color;
import tow.engine.map.Location;
import tow.game.client.ClientData;

import java.util.List;
import java.util.function.Consumer;

import static tow.game.client.menu.InterfaceStyles.*;

public abstract class MenuLocation extends Location {

    protected static class ButtonConfiguration {

        public String text;
        public MouseClickEventListener event;

        public ButtonConfiguration(String text, MouseClickEventListener event) {
            this.text = text;
            this.event = event;
        }
    }

    public MenuLocation(){
        super(Global.engine.render.getWidth(), Global.engine.render.getHeight());
        background = new tow.engine.map.Background(Color.GRAY, Color.GRAY);
    }

    public void addComponent(Component component){
        GameObject gameObject = GameObjectFactory.create(component.getPosition().x, component.getPosition().y);
        objAdd(gameObject);
        gameObject.setComponent(new GuiRender(component, (int) component.getSize().x, (int) component.getSize().y));
    }

    public void addComponent(Component component, int x, int y, int width, int height){
        GameObject gameObject = GameObjectFactory.create(x, y);
        objAdd(gameObject);
        gameObject.setComponent(new GuiRender(component, width, height));
        component.setPosition(x, y);
    }

    //Координаты (x;y) задают левый верхний угол компоненты
    public void addComponentLU(Component component, int x, int y, int width, int height){
        GameObject gameObject = GameObjectFactory.create(x+width/2, y+height/2);
        objAdd(gameObject);
        gameObject.setComponent(new GuiRender(component, width, height));
        component.setPosition(x, y);
    }

    public void addComponentToParent(Component component, int x, int y, int width, int height, Component parent){
        x += parent.getPosition().x;
        y += parent.getPosition().y;
        addComponent(component, x, y, width, height);
    }

    public void addComponentToParentLU(Component component, int x, int y, int width, int height, Component parent){
        x += parent.getPosition().x - parent.getSize().x/2;
        y += parent.getPosition().y - parent.getSize().y/2;
        addComponentLU(component, x, y, width, height);
    }

    public Panel createPanel(int x, int y, int width, int height) {
        Panel panel = new Panel();
        panel.setStyle(createPanelStyle());
        panel.setFocusable(false);

        addComponent(panel, x, y, width, height);
        panel.setPosition(x, y);
        return panel;
    }

    public Button createButton(String text, int x, int y, int width, int height, MouseClickEventListener event, Component parent){
        Button button = new Button(text);
        button.setStyle(createButtonStyle());
        button.getListenerMap().addListener(MouseClickEvent.class, event);

        addComponentToParentLU(button, x, y, width, height, parent);
        return button;
    }

    protected void createMenuButtons(List<ButtonConfiguration> buttonConfigurations){
        for(int i = 0; i < buttonConfigurations.size(); i++) {
            addComponent(createMenuButton(buttonConfigurations.get(i)), width/2, height/2 + (i * MENU_ELEMENT_HEIGHT) - (buttonConfigurations.size() * MENU_ELEMENT_HEIGHT)/2 + MENU_ELEMENT_HEIGHT/2, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);
        }
    }

    protected Button createMenuButton(ButtonConfiguration buttonConfiguration){
        Button button = new Button(buttonConfiguration.text);
        button.setStyle(createMenuButtonStyle());
        button.getListenerMap().addListener(MouseClickEvent.class, buttonConfiguration.event);
        button.getTextState().setFont(FontRegistry.ROBOTO_BOLD);
        button.getTextState().setFontSize(30);
        return button;
    }

    public TextAreaField createTextAreaField(int x, int y, int width, int height, Component parent) {
        TextAreaField textAreaField = new TextAreaField();
        textAreaField.setStyle(createTextAreaFieldStyle());

        addComponentToParentLU(textAreaField, x, y, width, height, parent);
        return textAreaField;
    }

    public void createToggleButton(int x, int y, int width, int height, Component parent) {
        ToggleButton toggleButton = new ToggleButton();

        addComponentToParentLU(toggleButton, x, y, width, height, parent);
    }

    public MouseClickEventListener getActivateLocationMouseReleaseListener(Class<? extends MenuLocation> menuLocationClass){
        return getMouseReleaseListener(event -> ClientData.menuLocationStorage.getMenuLocation(menuLocationClass).activate());
    }

    public MouseClickEventListener getMouseReleaseListener(Consumer<MouseClickEvent> mouseReleaseAction){
        return event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                mouseReleaseAction.accept(event);
            }
        };
    }
}
