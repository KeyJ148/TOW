package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.Vector2;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.QueueComponent;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.Position;
import org.liquidengine.legui.component.Component;

import java.util.Arrays;
import java.util.List;

public class GuiElement<T extends Component> extends QueueComponent {

    private final T component;
    private boolean moveComponentToGameObjectPosition;

    public GuiElement(T component) {
        this(component, false);
    }

    public GuiElement(T component, boolean moveComponentToGameObjectPosition) {
        this.component = component;
        this.moveComponentToGameObjectPosition = moveComponentToGameObjectPosition;
    }

    public GuiElement(T component, int weight, int height) {
        this(component, false, weight, height);
    }

    public GuiElement(T component, boolean moveComponentToGameObjectPosition, int weight, int height) {
        this(component, moveComponentToGameObjectPosition);
        component.setSize(weight, height);
    }

    @Override
    public void addToGameObject(GameObject gameObject) {
        super.addToGameObject(gameObject);
        getGameObject().getComponent(Position.class).location.addGUIComponent(component);
    }

    @Override
    public void destroy() {
        getGameObject().getComponent(Position.class).location.removeGUIComponent(component);
    }

    @Override
    public void updateComponent(long delta) {
        if (moveComponentToGameObjectPosition) {
            Vector2<Integer> relativePosition = getGameObject().getComponent(Position.class).getRelativePosition();
            component.setPosition(relativePosition.x, relativePosition.y);
        }
    }

    @Override
    protected void drawComponent() {
    }

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

    public Component getComponent() {
        return component;
    }

    public boolean isMoveComponentToGameObjectPosition() {
        return moveComponentToGameObjectPosition;
    }

    public void setMoveComponentToGameObjectPosition(boolean moveComponentToGameObjectPosition) {
        this.moveComponentToGameObjectPosition = moveComponentToGameObjectPosition;
    }
}
