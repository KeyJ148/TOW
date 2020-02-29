package tow.engine.obj.components.render;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import tow.engine.Vector2;
import tow.engine.obj.Obj;
import tow.engine.obj.components.Position;

public class GUIElement extends Rendering {

    private Component component;

    public GUIElement(Component component) {
        this.component = component;
    }

    public GUIElement(Component component, int width, int height) {
        this(component);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void addToObj(Obj obj){
        super.addToObj(obj);
        getObj().getComponent(Position.class).location.addGUIComponent(component);
    }

    @Override
    public void destroy() { }

    public Component getComponent(){
        return component;
    }

    @Override
    public void updateComponent(long delta) {
        Vector2<Integer> relativePosition = getObj().getComponent(Position.class).getRelativePosition();
        float xView = relativePosition.x - getWidth()/2;
        float yView = relativePosition.y - getHeight()/2;

        component.setPosition(xView, yView);

        if (getObj().isDestroy()) getObj().getComponent(Position.class).location.removeGUIComponent(component);
    }

    @Override
    protected void drawComponent() { }

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
