package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.gameobject.components.container.CachedComponentsContainer;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

public class GameObject extends CachedComponentsContainer implements Positionable {

    @Getter
    private final Location location;
    @Getter
    private double x, y;
    @Getter
    private double direction;
    @Getter
    private int z; //TODO вынести это свойство в Drawable интерфейс? И пробегаться не по игровым объектам, а по компонентам с drawable при отрисовке? Проблема в том, что глубины следующие: танк -> дом -> пушка, при текущей схеме танк и пушка должны быть разными игровыми объектами
    @Getter
    private boolean destroyed = false;

    /* TODO
        Можно по дефолту установить x, y, z = 0 и на Setter привязать функцию обновления чанка в локации и т.п.
     */
    public GameObject(Location location, double x, double y, int z) {
        this(location, x, y, z, Collections.emptyList());
    }

    public GameObject(Location location, double x, double y, int z, Collection<Component> initComponents) {
        this.location = location;
        this.x = x;
        this.y = y;
        this.z = z;
        location.add(this);
        for (Component component : initComponents) {
            addComponent(component);
        }
    }

    @Override
    public void addComponent(Component component) {
        component.setGameObject(this);
        super.addComponent(component);
        getLocation().runBeforeUpdateOnce(component::initialize);
    }

    /**
     * The object will be destroyed only at the end of his update cycle
     */
    public void destroy() {
        if (!destroyed) {
            destroyed = true;
            getLocation().runAfterUpdateOnce(this::destroyAfterUpdate);
        }
    }

    public Vector2<Double> getRelativePosition() {
        return getLocation().getCamera().toRelativePosition(getPosition());
    }

    public void setX(double x) {
        this.x = x;
        getLocation().updateObjectPosition(this);
    }

    public void setY(double y) {
        this.y = y;
        getLocation().updateObjectPosition(this);
    }

    public void setPosition(Vector2<Double> position) {
        setPosition(position.x, position.y);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        getLocation().updateObjectPosition(this);
    }

    public void setDirection(double direction) {
        this.direction = direction >= 0 ? direction % 360 : 360 - Math.abs(direction % 360);
    }

    private void destroyAfterUpdate() {
        getAllComponents().forEach(Component::destroy);
        getAllComponents().forEach(this::removeComponent);
        getLocation().remove(this);
    }
}
