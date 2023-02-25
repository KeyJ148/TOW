package cc.abro.orchengine.gameobject.location;

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
        objects.forEach(runnable);
    }

    @Deprecated
    public Set<T> getObjects() {
        return Collections.unmodifiableSet(objects);
    }

    public int objectsCount() {
        return objects.size();
    }
}
