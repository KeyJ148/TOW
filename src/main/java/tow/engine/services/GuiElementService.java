package tow.engine.services;

import org.liquidengine.legui.component.Component;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.GameObjectFactory;
import tow.engine.gameobject.components.render.GuiElement;
import tow.engine.map.Location;

public class GuiElementService {

    public void addComponentToLocation(Component component, Location location) {
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new GuiElement<>(component));
    }

    public void addComponentToLocation(Component component, int x, int y, Location location) {
        component.setPosition(x - component.getSize().x / 2, y - component.getSize().y / 2);
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new GuiElement<>(component));
    }

    //Координаты (x;y) задают левый верхний угол компоненты
    public void addComponentToLocationLU(Component component, int x, int y, Location location) {
        component.setPosition(x, y);
        GameObject gameObject = GameObjectFactory.create();
        location.objAdd(gameObject);
        gameObject.setComponent(new GuiElement<>(component));
    }
}
