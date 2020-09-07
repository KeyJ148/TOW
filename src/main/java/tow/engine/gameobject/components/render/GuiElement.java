package tow.engine.gameobject.components.render;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Panel;
import tow.engine.Vector2;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.QueueComponent;
import tow.engine.gameobject.components.Movement;
import tow.engine.gameobject.components.Position;

import java.util.Arrays;
import java.util.List;

public class GuiElement<T extends Component> extends QueueComponent {

    private Panel dummyPanel = new Panel();
    private T component;

    public GuiElement(T component) {
        this.component = component;
        dummyPanel.add(component);
    }

    public GuiElement(T component, int weight, int height) {
        this(component);
        component.setSize(weight, height);
    }

    @Override
    public void addToGameObject(GameObject gameObject){
        super.addToGameObject(gameObject);
        getGameObject().getComponent(Position.class).location.addGUIComponent(dummyPanel);
    }

    @Override
    public void destroy() {
        getGameObject().getComponent(Position.class).location.removeGUIComponent(component);
    }

    public Component getComponent(){
        return component;
    }

    @Override
    public void updateComponent(long delta) {
        Vector2<Integer> relativePosition = getGameObject().getComponent(Position.class).getRelativePosition();
        component.setPosition(relativePosition.x, relativePosition.y);
    }

    @Override
    protected void drawComponent() { }

    @Override
    public List<Class<? extends QueueComponent>> getPreliminaryUpdateComponents() {
        return Arrays.asList(Movement.class);
    }

    @Override
    public List<Class<? extends QueueComponent>> getPreliminaryDrawComponents() {
        return Arrays.asList();
    }

    @Override
    public Class getComponentClass() {
        return GuiElement.class;
    }
}
