package cc.abro.orchengine.services;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.components.render.CachedGuiElement;
import cc.abro.orchengine.gui.CachedComponent;
import cc.abro.orchengine.map.Location;
import org.liquidengine.legui.component.Component;

public class CachedGuiElementService {

    public GameObject addCachedComponentToLocationShiftedToCenter(CachedComponent<? extends Component> cachedComponent, Location location) {
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new CachedGuiElement<>(cachedComponent));
        return gameObject;
    }

    public <T extends Component> GameObject addCachedComponentToLocationShiftedToCenter(CachedComponent<T> cachedComponent, int x, int y, Location location) {
        cachedComponent.getComponent().setPosition(x - cachedComponent.getComponent().getSize().x / 2,
                y - cachedComponent.getComponent().getSize().y / 2);
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new CachedGuiElement<>(cachedComponent));
        return gameObject;
    }

    //Координаты (x;y) задают левый верхний угол компоненты
    public GameObject addCachedComponentToLocation(CachedComponent<? extends Component> cachedComponent, int x, int y, Location location) {
        cachedComponent.getComponent().setPosition(x, y);
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new CachedGuiElement<>(cachedComponent));
        return gameObject;
    }
}
