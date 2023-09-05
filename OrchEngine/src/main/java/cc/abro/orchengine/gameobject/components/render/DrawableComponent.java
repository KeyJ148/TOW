package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.PositionableComponent;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class DrawableComponent<T extends GameObject> extends PositionableComponent<T> implements Drawable {

    private final Set<Consumer<Drawable>> listeners = new HashSet<>();

    @Getter
    private int z;

    public void setZ(int z) {
        this.z = z;
        notifyChangeZListeners();
    }

    @Override
    public void addChangeZListener(Consumer<Drawable> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeZListener(Consumer<Drawable> listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyChangeZListeners() {
        listeners.forEach(listener -> listener.accept(this));
    }
}
