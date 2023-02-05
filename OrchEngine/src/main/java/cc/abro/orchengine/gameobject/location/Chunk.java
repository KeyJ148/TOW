package cc.abro.orchengine.gameobject.location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Chunk<T> {

    public static final int DEFAULT_SIZE = 100;

    private final Set<T> objects = new HashSet<>();

    public void add(T object) {
        objects.add(object);
    }

    public void remove(T object) {
        objects.remove(object);
    }

    public void runForAll(Consumer<T> runnable) {
        //Делаем копию сета, иначе получаем ConcurrentModificationException,
        //т.к. во время апдейта можно создать новый объект и этот объект будет помещен в сет
        new ArrayList<>(objects).forEach(runnable);  //TODO в подобных местах использовать Collections.unmodifiableSet() ?
    }

    @Deprecated
    public Set<T> getObjects() {
        return Collections.unmodifiableSet(objects);
    }

    public int objectsCount() {
        return objects.size();
    }
}
