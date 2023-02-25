package cc.abro.orchengine.gameobject.components.interfaces;

import java.util.Collections;
import java.util.List;

public interface Updatable extends ComponentCollection {

    /**
     * Method will call when game world will updating. All components from {@link #getPreliminaryUpdateComponents} will
     * update, and after that this component will be updated.
     *
     * @param delta The count of nanoseconds passed since last update
     */
    void update(long delta);

    /**
     * @return List of components that needed to {@link #update} before {@link #update} this component.
     */
    default List<Class<? extends Updatable>> getPreliminaryUpdateComponents() {
        return Collections.emptyList();
    }
}
