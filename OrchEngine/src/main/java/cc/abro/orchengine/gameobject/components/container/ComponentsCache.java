package cc.abro.orchengine.gameobject.components.container;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Positionable;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class ComponentsCache {

    @Getter
    private final Set<Positionable> positionableComponents = new HashSet<>();

    public void addComponent(Component component) {
        if (component instanceof Positionable positionableComponent){
            positionableComponents.add(positionableComponent);
        }
    }

    public void removeComponent(Component component) {
        if (component instanceof Positionable positionableComponent){
            positionableComponents.remove(positionableComponent);
        }
    }
}
