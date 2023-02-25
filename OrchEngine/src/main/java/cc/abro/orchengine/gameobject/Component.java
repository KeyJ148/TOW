package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.services.ServiceConsumer;
import lombok.Getter;

public abstract class Component implements ServiceConsumer {

    @Getter
    private GameObject gameObject;

    public void setGameObject(GameObject gameObject) {
        if (getGameObject() != null) {
            throw new IllegalStateException("Component can be contained in one GameObject only");
        }
        this.gameObject = gameObject;
    }

    public void initialize() {}

    public void destroy() {}

    public Class<? extends Component> getComponentClass() {
        return getClass();
    }

}
