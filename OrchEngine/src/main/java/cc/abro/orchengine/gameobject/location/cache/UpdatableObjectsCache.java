package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UpdatableObjectsCache {

    private final Set<Updatable> updatableObjects = new HashSet<>();
    private final Set<Runnable> beforeUpdateActions = new HashSet<>();
    private final Set<Runnable> afterUpdateActions = new HashSet<>();

    //TODO статистика по обновленным объектам? Или уровнем выше, т.к. это тупо размер массива?
    @Getter //TODO stats
    private int chunksUpdated = 0; //Кол-во обновленных чанков
    @Getter
    private int objectsUpdated = 0; //Кол-во обновленных объектов

    public void add(Updatable updatable) {
        updatableObjects.add(updatable);
    }

    public void remove(Updatable updatable) {
        updatableObjects.remove(updatable);
    }

    public void update(long delta) {
        beforeUpdateActions.forEach(Runnable::run);
        Collections.unmodifiableSet(updatableObjects).forEach(object -> object.update(delta));
        afterUpdateActions.forEach(Runnable::run);

        beforeUpdateActions.clear();
        afterUpdateActions.clear();
    }

    public void runBeforeUpdateOnce(Runnable runnable) {
        beforeUpdateActions.add(runnable);
    }

    public void runAfterUpdateOnce(Runnable runnable) {
        afterUpdateActions.add(runnable);
    }
}
