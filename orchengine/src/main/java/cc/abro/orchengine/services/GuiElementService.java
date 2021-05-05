package cc.abro.orchengine.services;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.components.render.GuiElement;
import cc.abro.orchengine.map.Location;
import org.liquidengine.legui.component.Component;

public class GuiElementService {

    public GameObject addComponentToLocationShiftedToCenter(Component component, Location location) {
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new GuiElement<>(component));
        return gameObject;
    }

    public GameObject addComponentToLocationShiftedToCenter(Component component, int x, int y, Location location) {
        component.setPosition(x - component.getSize().x / 2, y - component.getSize().y / 2);
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new GuiElement<>(component));
        return gameObject;
    }

    //Координаты (x;y) задают левый верхний угол компоненты
    public GameObject addComponentToLocation(Component component, int x, int y, Location location) {
        component.setPosition(x, y);
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new GuiElement<>(component));
        return gameObject;
    }
}
