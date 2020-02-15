package tow.engine.obj.components.render;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.obj.Obj;

public class GUIElement extends Rendering {

    private Component component;

    public GUIElement(Component component, Obj obj) {
        super(obj);
        this.component = component;
        getObj().position.room.addGUIComponent(component);
    }

    public GUIElement(Component component, int width, int height, Obj obj) {
        this(component, obj);
        setWidth(width);
        setHeight(height);
    }

    public Component getComponent(){
        return component;
    }

    @Override
    public void update(long delta) {
        Vector2<Integer> relativePosition = getObj().position.getRelativePosition();
        float xView = relativePosition.x - getWidth()/2;
        float yView = relativePosition.y - getHeight()/2;

        component.setPosition(xView, yView);

        if (getObj().destroy) getObj().position.room.removeGUIComponent(component);
    }

    @Override
    public int getWidthTexture() {
        return (int) component.getSize().x;
    }

    @Override
    public int getHeightTexture() {
        return (int) component.getSize().y;
    }

    @Override
    public int getWidth() {
        return getWidthTexture();
    }

    @Override
    public int getHeight() {
        return getHeightTexture();
    }

    @Override
    public void setWidth(int width) {
        Vector2f size = component.getSize();
        size.x = width;
        component.setSize(size);
    }

    @Override
    public void setHeight(int height) {
        Vector2f size = component.getSize();
        size.y = height;
        component.setSize(size);
    }

    @Override
    public void setDefaultSize() {
        throw new UnsupportedOperationException("GUIElement not have default size");
    }
}
