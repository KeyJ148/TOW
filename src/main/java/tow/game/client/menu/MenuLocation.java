package tow.game.client.menu;

import org.liquidengine.legui.component.Component;
import tow.engine.Global;
import tow.engine.image.Color;
import tow.engine.map.Background;
import tow.engine.map.Location;
import tow.engine.obj.Obj;
import tow.engine.obj.components.render.GUIElement;

public class MenuLocation extends Location {

    protected final static int MENU_ELEMENT_WIDTH = 250;
    protected final static int MENU_ELEMENT_HEIGHT = 70;
    protected final static int MENU_TEXT_FIELD_HEIGHT = 30;

    public MenuLocation(){
        super(Global.engine.render.getWidth(), Global.engine.render.getHeight(), true);
        background = new Background(tow.engine.image.Color.GRAY, Color.GRAY);
        activate();
    }

    public void createComponent(Component component){
        Obj obj = new Obj(component.getPosition().x, component.getPosition().y, 0);
        objAdd(obj);
        obj.rendering = new GUIElement(component, (int) component.getSize().x, (int) component.getSize().y, obj);
    }

    public void createComponent(Component component, int x, int y, int width, int height){
        Obj obj = new Obj(x, y, 0);
        objAdd(obj);
        obj.rendering = new GUIElement(component, width, height, obj);
    }
}
