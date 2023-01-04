package cc.abro.orchengine.gameobject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ComponentsContainer extends Component {

    private final Map<Class<? extends Component>, Component> components = new HashMap<>();

    public ComponentsContainer() {
        this(Collections.emptyList());
    }

    //TODO валидация, что нет циклов в графе компонент
    public ComponentsContainer(Collection<Component> initComponents) {
        for (Component component : initComponents) {
            setComponent(component);
        }
    }

    //TODO валидация, что нет циклов в графе компонент
    public void setComponent(Component component) {
        component.notifyAboutAddToComponentsContainer(this);
        components.put(component.getComponentClass(), component);
    }

    public <T extends Component> T getComponent(Class<T> classComponent) {
        return (T) components.get(classComponent);
    }

    public boolean hasComponent(Class<? extends Component> classComponent) {
        return getComponent(classComponent) != null;
    }

    public void removeComponent(Class<? extends Component> classComponent) {
        components.remove(classComponent);
    }

    /**
     * Оповещение, что нас добавили в контейнер как компонент. Родительский контейнер может пока не быть привязанным к
     * GameObject. Но если он уже привязан, то мы должны оповестить все дочерние компоненты,
     * что нас добавили к GameObject.
     *
     * @param parentContainer Родительский контейнер, на который текущий контейнер добавили как компонент
     */
    @Override
    protected void notifyAboutAddToComponentsContainer(ComponentsContainer parentContainer) {
        super.notifyAboutAddToComponentsContainer(parentContainer);
        if (parentContainer.getGameObject() != null) {
            notifyAboutAddToGameObject(parentContainer.getGameObject());
        }
    }

    @Override
    protected void notifyAboutAddToGameObject(GameObject gameObject) {
        super.notifyAboutAddToGameObject(gameObject);
        components.values().forEach(c -> c.notifyAboutAddToGameObject(gameObject));
    }

    /**
     * Method will call when game world will updating. All components from {@link #getPreliminaryUpdateComponents} will
     * update, and after that this component will be updated in {@link #updateIfNeed(long)}. //TODO fix
     *
     * @param delta The count of nanoseconds passed since last update
     */
    @Override
    public void update(long delta) {
        components.values().forEach(Component::startNewStep);
        components.values().stream()
                .filter(c -> c.getComponentClass() != getComponentClass())
                .peek(c -> updatePreliminaryComponents(c, delta))
                .forEach(c -> c.updateIfNeed(delta));
        //Контейнеры компонентов обрабатываем в последнюю очередь
        components.values().stream()
                .filter(c -> c.getComponentClass() == getComponentClass())
                .forEach(c -> c.updateIfNeed(delta));
    }

    /**
     * Обновляет только компоненты, от которых зависит переданный компонент
     *
     * @param component Компонент для которого необходимо обновить зависимости, сам обновлен не будет
     * @param delta     The count of nanoseconds passed since last update //TODO fix
     */
    public void updatePreliminaryComponents(Component component, long delta) {
        for (Class<? extends Component> componentClass : component.getPreliminaryUpdateComponents()) {
            if (componentClass == getComponentClass())
                continue; //Контейнеры компонентов обрабатываем в последнюю очередь
            if (hasComponent(componentClass)) {//TODO getGameObject() change to getComponentsContainer()
                Component needUpdateComponent = getComponent(componentClass); //TODO поменять на получение списка
                needUpdateComponent.updateIfNeed(delta);
            }
        }
    }

    /**
     * Method will call when game world will drawing. All components from {@link #getPreliminaryDrawComponents} will
     * draw, and after that this component will be draw in {@link #drawIfNeed()}. //TODO fix
     */
    @Override
    public void draw() { //TODO как отрисовывать, когда у разных спрайтов разные глубины? Зачем вообще порядок отрисовки?
        components.values().forEach(Component::draw);
        components.values().stream()
                .filter(c -> c.getComponentClass() != getComponentClass())
                .peek(this::drawPreliminaryComponents)
                .forEach(Component::drawIfNeed);
        //Контейнеры компонентов обрабатываем в последнюю очередь
        components.values().stream()
                .filter(c -> c.getComponentClass() == getComponentClass())
                .forEach(Component::drawIfNeed);
    }

    /**
     * Отрисовывает только компоненты, от которых зависит переданный компонент
     *
     * @param component Компонент для которого необходимо отрисовать зависимости, сам обновлен не будет //TODO fix it
     */
    public void drawPreliminaryComponents(Component component) {
        for (Class<? extends Component> componentClass : component.getPreliminaryUpdateComponents()) {
            if (componentClass == getComponentClass())
                continue; //Контейнеры компонентов обрабатываем в последнюю очередь
            if (hasComponent(componentClass)) {//TODO getGameObject() change to getComponentsContainer()
                Component needUpdateComponent = getComponent(componentClass); //TODO поменять на получение списка
                needUpdateComponent.drawIfNeed();
            }
        }
    }

    @Override
    public void destroy() {
        components.values().forEach(Component::destroy);
    }

    @Override
    public Class<ComponentsContainer> getComponentClass() {
        return ComponentsContainer.class;
    }
}
