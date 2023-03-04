package cc.abro.orchengine.gameobject.components.collision;

import cc.abro.orchengine.gameobject.components.PositionableComponent;
import cc.abro.orchengine.gameobject.components.interfaces.Collidable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class CollidableComponent extends PositionableComponent implements Collidable {

    private final Set<Consumer<Collidable>> changeCollidableObjectsListeners = new HashSet<>(); //Оповещаем этих слушателей при изменении списка #collidableObjects
    private final Set<Class<? extends Collidable>> collidableObjects = new HashSet<>(); //Список объектов с которыми надо проверять столкновения //TODO classes, not objects

    public void addCollidableObjects(Class[] collidableObjects) {
        for (Class obj : collidableObjects)
            addCollidableObject(obj);
    }

    public void addCollidableObject(Class collidableObject) {
        collidableObjects.add(collidableObject);
        notifyChangeCollidableObjectsListeners();
    }

    public void removeCollidableObject(Class collidableObject) {
        collidableObjects.remove(collidableObject);
        notifyChangeCollidableObjectsListeners();
    }

    @Override
    public Set<Class<? extends Collidable>> getCollidableObjects() {
        return Collections.unmodifiableSet(collidableObjects);
    }

    @Override
    public void addChangeCollidableObjectsListener(Consumer<Collidable> listener) {
        changeCollidableObjectsListeners.add(listener);
    }

    @Override
    public void removeChangeCollidableObjectsListener(Consumer<Collidable> listener) {
        changeCollidableObjectsListeners.remove(listener);
    }

    @Override
    public void notifyChangeCollidableObjectsListeners() {
        changeCollidableObjectsListeners.forEach(listener -> listener.accept(this));
    }
}
