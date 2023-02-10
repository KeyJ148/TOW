package cc.abro.orchengine.gameobject.components.container;

import cc.abro.orchengine.gameobject.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class ListeningComponentsContainer extends ComponentsContainer {

    public enum ComponentEvent {
        ADD, REMOVE;
    }

    private final Set<BiConsumer<Component, ComponentEvent>> listeners = new HashSet<>();

    public void addListener(BiConsumer<Component, ComponentEvent> listener) {
        listeners.add(listener);
    }

    public void removeListener(BiConsumer<Component, ComponentEvent> listener) {
        listeners.remove(listener);
    }

    @Override
    public void addComponent(Component component) {
        super.addComponent(component);
        listeners.forEach(l -> l.accept(component, ComponentEvent.ADD));
    }

    @Override
    public void removeComponent(Component component) {
        super.removeComponent(component);
        listeners.forEach(l -> l.accept(component, ComponentEvent.REMOVE));
    }

    @Override
    public void removeComponents(Class<? extends Component> classComponent) {
        for (Component component : getComponents(classComponent)) {
            listeners.forEach(l -> l.accept(component, ComponentEvent.REMOVE));
        }
        super.removeComponents(classComponent);
    }
}
