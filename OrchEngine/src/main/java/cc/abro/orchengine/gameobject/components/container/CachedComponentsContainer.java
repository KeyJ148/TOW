package cc.abro.orchengine.gameobject.components.container;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;

public class CachedComponentsContainer extends ComponentsContainer {

    private final ComponentsCache componentsCache = new ComponentsCache();

    public void update(long delta) { //TODO запретить Override в наследниках (final). Если надо сделать кастомную логику, то просто создай обычный или анонимный компонент
        componentsCache.getUpdatableComponents().forEach(c -> c.update(delta));
    }

    public void draw() {  //TODO запретить Override в наследниках (final). Если надо сделать кастомную логику, то просто создай обычный или анонимный компонент
        componentsCache.getDrawableComponents().forEach(Drawable::draw);
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

     /* TODO вынести куда-то во внешний Updater, который будет заниматься вызовом функций update (или в этом классе оставить, или создать ещё один дочерний класс)
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
    }*/
    /**
     * Обновляет только компоненты, от которых зависит переданный компонент
     *
     * @param component Компонент для которого необходимо обновить зависимости, сам обновлен не будет
     * @param delta     The count of nanoseconds passed since last update //TODO fix
     */
    /* TODO вынести куда-то во внешний Updater, который будет заниматься вызовом функций update
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

    //TODO это свойство компонента
    private boolean updatedInThisStep = false;
    void updateIfNeed(long delta) {
        if (updatedInThisStep) return;
        update(delta);
        updatedInThisStep = true;
    }
    void startNewStep() {
        updatedInThisStep = false;
    }*/
}
