package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.container.ComponentsContainer;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;

import java.util.HashSet;
import java.util.Set;

public class UpdatableObjectsCache {

    private final Set<Updatable> updatableObjects = new HashSet<>();
    private final Set<Runnable> beforeUpdateActions = new HashSet<>();
    private final Set<Runnable> afterUpdateActions = new HashSet<>();
    private final Set<Updatable> wasUpdatedInThisStep = new HashSet<>();

    public void add(Updatable updatable) {
        updatableObjects.add(updatable);
    }

    public void remove(Updatable updatable) {
        updatableObjects.remove(updatable);
    }

    public void update(long delta) {
        beforeUpdateActions.forEach(Runnable::run);
        beforeUpdateActions.clear();

        updatableObjects.forEach(object -> updateObject(object, delta));
        wasUpdatedInThisStep.clear();

        afterUpdateActions.forEach(Runnable::run);
        afterUpdateActions.clear();
    }

    public void runBeforeUpdateOnce(Runnable runnable) {
        beforeUpdateActions.add(runnable);
    }

    public void runAfterUpdateOnce(Runnable runnable) {
        afterUpdateActions.add(runnable);
    }

    public int getCountUpdatableObjects() {
        return updatableObjects.size();
    }

    private void updateObject(Updatable updatable, long delta) {
        if (wasUpdatedInThisStep.contains(updatable)) {
            return;
        }

        updatePreliminaryObjects(updatable, delta);
        updatable.update(delta);
        wasUpdatedInThisStep.add(updatable);
    }

    /**
     * Обновляет только компоненты, от которых зависит переданный компонент
     *
     * @param updatable Компонент для которого необходимо обновить зависимости, сам обновлен не будет
     * @param delta     The count of nanoseconds passed since last update
     */
    private void updatePreliminaryObjects(Updatable updatable, long delta) {
        ComponentsContainer container = updatable.getComponentsContainer();
        for (Class<? extends Updatable> updatableClass : updatable.getPreliminaryUpdateComponents()) {
            Class<? extends Component> componentClass = updatableClass.asSubclass(Component.class);
            if (container.hasComponent(componentClass)) {
                Set<? extends Component> needUpdateComponents = container.getComponents(componentClass);
                for (Component needUpdateComponent : needUpdateComponents) {
                    updateObject((Updatable) needUpdateComponent, delta);
                }
            }
        }
    }
}
