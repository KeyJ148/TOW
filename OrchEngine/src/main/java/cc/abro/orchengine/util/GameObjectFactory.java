package cc.abro.orchengine.util;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.resources.textures.Texture;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class GameObjectFactory {

    public GameObject create(Location location) {
        return create(location, 0, 0);
    }

    public GameObject create(Location location, double x, double y) {
        return create(location, x, y, 0);
    }

    public GameObject create(Location location, double x, double y, double directionDraw) {
        GameObject gameObject = new GameObject(location);
        gameObject.setPosition(x, y);
        gameObject.setDirection(directionDraw);

        return gameObject;
    }

    public GameObject create(Location location, double x, double y, int z, double directionDraw, Texture texture) {
        GameObject gameObject = create(location, x, y, directionDraw);
        gameObject.addComponent(new SpriteRender<>(texture, z));

        return gameObject;
    }

    public GameObject create(Location location, double x, double y, int z, double directionDraw, List<Texture> textures) {
        GameObject gameObject = create(location, x, y, directionDraw);
        gameObject.addComponent(new AnimationRender<>(textures, z));

        return gameObject;
    }

    public GameObject create(Location location, Component<GameObject> component) {
        GameObject gameObject = create(location, 0, 0);
        gameObject.addComponent(component);
        return gameObject;
    }
}
