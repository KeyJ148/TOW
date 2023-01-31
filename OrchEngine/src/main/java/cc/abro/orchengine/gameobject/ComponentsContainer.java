package cc.abro.orchengine.gameobject;

import java.util.*;
import java.util.stream.Collectors;

public class ComponentsContainer {

    private final Map<Class<? extends Component>, Set<Component>> components = new HashMap<>();

    //TODO валидация, что нет циклов в графе компонент, если компонент Updatable
    public void addComponent(Component component) {
        components.computeIfAbsent(component.getComponentClass(), k -> new HashSet<>()).add(component);
    }

    public <T extends Component> T getComponent(Class<T> classComponent) {
        return getOptionalComponent(classComponent).get();
    }

    public <T extends Component> Optional<T> getOptionalComponent(Class<T> classComponent) {
        return getComponents(classComponent).stream().findFirst();
    }

    public <T extends Component> Set<T> getComponents(Class<T> classComponent) {
        return Collections.unmodifiableSet(getComponentChangeableSet(classComponent));
    }

    public Set<Component> getAllComponents() {
        return components.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public void removeComponent(Component component) {
        getComponentChangeableSet(component.getComponentClass()).remove(component);
    }

    public void removeComponents(Class<? extends Component> classComponent) {
        components.remove(classComponent);
    }

    public boolean hasComponent(Class<? extends Component> classComponent) {
        return getOptionalComponent(classComponent).isPresent();
    }

    protected <T extends Component> Set<T> getComponentChangeableSet(Class<T> classComponent) {
        Set<Component> result = components.getOrDefault(classComponent, Collections.emptySet());
        return (Set<T>) result;
    }
}
