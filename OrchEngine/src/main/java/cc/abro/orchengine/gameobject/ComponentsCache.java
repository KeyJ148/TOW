package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class ComponentsCache {

    @Getter
    private final Set<Updatable> updatableComponents = new HashSet<>();
    @Getter
    private final Set<Drawable> drawableComponents = new HashSet<>();

    public void addComponent(Component component) {
        if (component instanceof Updatable updatableComponent){
            updatableComponents.add(updatableComponent);
        }
        if (component instanceof Drawable drawableComponent){
            drawableComponents.add(drawableComponent);
        }
    }

    public void removeComponent(Component component) {
        if (component instanceof Updatable updatableComponent){
            updatableComponents.remove(updatableComponent);
        }
        if (component instanceof Drawable drawableComponent){
            drawableComponents.remove(drawableComponent);
        }
    }
}
