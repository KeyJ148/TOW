package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.gameobject.components.container.ComponentsContainer;
import cc.abro.orchengine.gameobject.components.interfaces.ComponentCollection;
import cc.abro.orchengine.services.ServiceConsumer;
import lombok.Getter;

public abstract class Component<T extends GameObject> implements ServiceConsumer, ComponentCollection {

    @Getter
    private T gameObject;

    public void setGameObject(T gameObject) {
        if (getGameObject() != null) {
            throw new IllegalStateException("Component can be contained in one GameObject only");
        }
        this.gameObject = gameObject;
    }

    public void postEvent(Object event) {
        getGameObject().postEvent(event);
    }

    public void initialize() {}

    public void destroy() {}

    public Class<? extends Component> getComponentClass() {
        return getClass();
    }

    @Override
    public ComponentsContainer getComponentsContainer() {
        return gameObject;
    }
}
