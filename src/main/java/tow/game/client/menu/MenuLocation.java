package tow.game.client.menu;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import tow.engine.Global;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.GameObjectFactory;
import tow.engine.gameobject.components.render.GuiRender;
import tow.engine.image.Color;
import tow.engine.map.Location;

import static tow.game.client.menu.InterfaceStyles.*;
import static tow.game.client.menu.InterfaceStyles.MENU_ELEMENT_HEIGHT;

public abstract class MenuLocation extends Location {

    public MenuLocation(){
        super(Global.engine.render.getWidth(), Global.engine.render.getHeight());
        background = new tow.engine.map.Background(Color.GRAY, Color.GRAY);
        activate();
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
        panel.setStyle(PANEL_STYLE);
        panel.setFocusable(false);

        addComponent(panel, x, y, width, height);
        panel.setPosition(x, y);
        return panel;
    }

    public void createButton(String text, int x, int y, int width, int height, MouseClickEventListener event, Component parent){
        Button button = new Button(text);
        button.setStyle(BUTTON_STYLE);
        button.getListenerMap().addListener(MouseClickEvent.class, event);

        addComponentToParentLU(button, x, y, width, height, parent);
    }

    public void createMenuButton(String text, int pos, MouseClickEventListener event){
        Button button = new Button(text);
        button.setStyle(MENU_BUTTON_STYLE);
        button.getListenerMap().addListener(MouseClickEvent.class, event);

        addComponent(button, width/2, height/2+pos*MENU_ELEMENT_HEIGHT, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);
    }

    public TextAreaField createTextAreaField(int x, int y, int width, int height, Component parent) {
        TextAreaField textAreaField = new TextAreaField();
        textAreaField.setStyle(TEXT_AREA_FIELD_STYLE);

        addComponentToParentLU(textAreaField, x, y, width, height, parent);
        return textAreaField;
    }
}
