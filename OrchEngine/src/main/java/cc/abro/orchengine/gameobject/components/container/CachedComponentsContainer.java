package cc.abro.orchengine.gameobject.components.container;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;

public class CachedComponentsContainer extends ListeningComponentsContainer {

    private final ComponentsCache componentsCache = new ComponentsCache();

    public void notifyComponentsAboutUpdatePosition() {
        componentsCache.getPositionableComponents().forEach(Positionable::notifyChangePositionListeners);
    }

    @Override
    public void addComponent(Component component) {
        super.addComponent(component);
        componentsCache.addComponent(component);
    }

    @Override
    public void removeComponent(Component component) {
        super.removeComponent(component);
        componentsCache.removeComponent(component);
    }

    @Override
    public void removeComponents(Class<? extends Component> classComponent) {
        super.removeComponents(classComponent);
        getComponents(classComponent).forEach(componentsCache::removeComponent);
    }
}
