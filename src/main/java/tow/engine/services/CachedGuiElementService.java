package tow.engine.services;

import org.liquidengine.legui.component.Component;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.GameObjectFactory;
import tow.engine.gameobject.components.render.CachedGuiElement;
import tow.engine.gui.CachedComponent;
import tow.engine.map.Location;

public class CachedGuiElementService {

    public void addCachedComponentToLocation(CachedComponent<? extends Component> cachedComponent, Location location) {
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new CachedGuiElement<>(cachedComponent));
    }

    public <T extends Component> void addCachedComponentToLocation(CachedComponent<T> cachedComponent, int x, int y, Location location) {
        cachedComponent.getComponent().setPosition(x - cachedComponent.getComponent().getSize().x / 2,
                y - cachedComponent.getComponent().getSize().y / 2);
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new CachedGuiElement<>(cachedComponent));
    }

    //Координаты (x;y) задают левый верхний угол компоненты
    public void addCachedComponentToLocationLU(CachedComponent<? extends Component> cachedComponent, int x, int y, Location location) {
        cachedComponent.getComponent().setPosition(x, y);
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new CachedGuiElement<>(cachedComponent));
    }
}
