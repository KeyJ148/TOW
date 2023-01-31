package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.location.Location;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

public class GameObject extends LocatableComponentsContainer {

    @Getter
    private boolean destroyed = false;

    public GameObject(Location location) {
        super(location, Collections.emptyList());
    }

    public GameObject(Location location, Collection<Component> initComponents) {
        super(location, initComponents);
        location.add(this); //TODO вынести в LocatableComponentContainer? В Location, кажется, достаточно его. Или вообще переименовать LocatableCompContainer в GameObject?
    }

    @Override
    public void addComponent(Component component) {
        super.addComponent(component);
        component.setGameObject(this);
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

    private void destroyAfterUpdate() {
        getAllComponents().forEach(Component::destroy);
        getLocation().remove(this);
    }
}
