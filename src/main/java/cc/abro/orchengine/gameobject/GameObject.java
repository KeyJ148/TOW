package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.location.Location;

import java.util.ArrayList;
import java.util.Collection;

public class GameObject {

    private final Location.ObjectHolder locationHolder = new Location.ObjectHolder();
    private final ComponentsContainer components;
    private boolean destroying = false;
    private boolean destroyed = false;

    public GameObject() {
        this(new ArrayList<>());
    }

    public GameObject(Collection<Component> initComponents) {
        this(new ComponentsContainer(initComponents));
    }

    public GameObject(ComponentsContainer components) {
        this.components = components;
        components.notifyAboutAddToGameObject(this);
    }

    public void update(long delta) {
        components.update(delta);

        if (destroying) {
            components.destroy();
            getLocation().remove(this);
            destroying = false;
            destroyed = true;
        }
    }

    public void draw() {
        components.draw();
    }

    /**
     * The object will be destroyed only at the end of his update cycle
     */
    public void destroy() {
        if (!destroyed) {
            destroying = true;
        }
    }

    public boolean isDestroy() {
        return destroying || destroyed;
    }

    public Location.ObjectHolder getLocationHolder() {
        return locationHolder;
    }

    /*
     * Просто прокси методы
     */
    public Location getLocation() {
        return locationHolder.getLocation();
    }

    public void setComponent(Component component) {
        components.setComponent(component);
        component.notifyAboutAddToGameObject(this);
    }

    public <T extends Component> T getComponent(Class<T> classComponent) {
        return components.getComponent(classComponent);
    }

    public boolean hasComponent(Class<? extends Component> classComponent) {
        return components.hasComponent(classComponent);
    }

    public void removeComponent(Class<? extends Component> classComponent) {
        components.removeComponent(classComponent);
    }
}
