package cc.abro.orchengine.gameobject.components.container;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class ComponentsCache {

    @Getter
    private final Set<Updatable> updatableComponents = new HashSet<>();
    @Getter
    private final Set<Drawable> drawableComponents = new HashSet<>();
    @Getter
    private final Set<Positionable> positionableComponents = new HashSet<>();

    public void addComponent(Component component) {
        if (component instanceof Updatable updatableComponent){
            updatableComponents.add(updatableComponent);
        }
        if (component instanceof Drawable drawableComponent){
            drawableComponents.add(drawableComponent);
        }
        if (component instanceof Positionable positionableComponent){
            positionableComponents.add(positionableComponent);
        }
    }

    public void removeComponent(Component component) {
        if (component instanceof Updatable updatableComponent){
            updatableComponents.remove(updatableComponent);
        }
        if (component instanceof Drawable drawableComponent){
            drawableComponents.remove(drawableComponent);
        }
        if (component instanceof Positionable positionableComponent){
            positionableComponents.remove(positionableComponent);
        }
    }
}
