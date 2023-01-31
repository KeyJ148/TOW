package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.location.Location;
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

    public GameObject create(Location location, double x, double y, int z) {
        return new GameObject(location, List.of(new Position(x, y, z)));
    }

    public GameObject create(Location location, double x, double y, int z, double directionDraw) {
        GameObject gameObject = create(location, x, y, z);
        gameObject.getComponent(Position.class).setDirectionDraw(directionDraw);

        return gameObject;
    }

    public GameObject create(Location location, double x, double y, int z, double directionDraw, Texture texture) {
        GameObject gameObject = create(location, x, y, z, directionDraw);
        gameObject.addComponent(new SpriteRender(texture));

        return gameObject;
    }

    public GameObject create(Location location, double x, double y, int z, double directionDraw, List<Texture> textures) {
        GameObject gameObject = create(location, x, y, z, directionDraw);
        gameObject.addComponent(new AnimationRender(textures));

        return gameObject;
    }

    public GameObject create(Location location, Component component) {
        GameObject gameObject = create(location, 0, 0);
        gameObject.addComponent(component);
        return gameObject;
    }
}
