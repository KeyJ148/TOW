package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.components.container.ComponentsCache;
import cc.abro.orchengine.gameobject.components.container.ComponentsContainer;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

public class GameObject extends ComponentsContainer {

    @Getter
    private final Location location;
    @Getter
    private boolean destroyed = false;
    @Getter
    private double x, y;
    @Getter
    private int z; //TODO вынести это свойство в Drawable интерфейс? И пробегаться не по игровым объектам, а по компонентам с drawable при отрисовке? Проблема в том, что глубины следующие: танк -> дом -> пушка, при текущей схеме танк и пушка должны быть разными игровыми объектами
    private final ComponentsCache componentsCache = new ComponentsCache();

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

    public void update(long delta) { //TODO запретить Override в наследниках. Если надо сделать кастомную логику, то просто создай обычный или анонимный компонент
        componentsCache.getUpdatableComponents().forEach(c -> c.update(delta));
    }

    public void draw() {
        componentsCache.getDrawableComponents().forEach(Drawable::draw);
    }

    @Override
    public void addComponent(Component component) {
        super.addComponent(component);
        componentsCache.addComponent(component);
        component.setGameObject(this);

        getLocation().runBeforeUpdateOnce(component::initialize);
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

    /**
     * The object will be destroyed only at the end of his update cycle
     */
    public void destroy() {
        if (!destroyed) {
            destroyed = true;
            getLocation().runAfterUpdateOnce(this::destroyAfterUpdate);
        }
    }

    public Vector2<Integer> getRelativePosition() {
        return Context.getService(LocationManager.class).getActiveLocation().getCamera().toRelativePosition(new Vector2<>((int) x, (int) y));
    }

    private void destroyAfterUpdate() {
        getAllComponents().forEach(Component::destroy);
        getLocation().remove(this);
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
