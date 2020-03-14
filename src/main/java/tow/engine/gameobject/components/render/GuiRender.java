package tow.engine.gameobject.components.render;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import tow.engine.Vector2;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Position;

public class GuiRender extends Rendering {

    private Component component;

    public GuiRender(Component component) {
        this.component = component;
    }

    public GuiRender(Component component, int width, int height) {
        this(component);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void addToGameObject(GameObject gameObject){
        super.addToGameObject(gameObject);
        getGameObject().getComponent(Position.class).location.addGUIComponent(component);
    }

    @Override
    public void destroy() { }

    public Component getComponent(){
        return component;
    }

    @Override
    public void updateComponent(long delta) {
        Vector2<Integer> relativePosition = getGameObject().getComponent(Position.class).getRelativePosition();
        float xView = relativePosition.x - getWidth()/2;
        float yView = relativePosition.y - getHeight()/2;

        component.setPosition(xView, yView);

        if (getGameObject().isDestroy()) getGameObject().getComponent(Position.class).location.removeGUIComponent(component);
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
