package cc.abro.orchengine.gameobject.components.interfaces;

import cc.abro.orchengine.gameobject.Component;

import java.util.Collections;
import java.util.List;

public interface Updatable {

    /**
     * Method will call when game world will updating. All components from {@link #getPreliminaryUpdateComponents} will
     * update, and after that this component will be updated in {@link #updateIfNeed(long)}. //TODO fix
     *
     * @param delta The count of nanoseconds passed since last update
     */
    void update(long delta);

    /**
     * @return List of components that needed to {@link #update} before {@link #update} this component.
     */
    default List<Class<? extends Component>> getPreliminaryUpdateComponents() {
        return Collections.emptyList();
    }
}
