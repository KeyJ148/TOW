package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.resources.textures.Texture;

import java.util.List;

public class GameObjectFactory {

    public static GameObject create() {
        return create(0, 0);
    }

    public static GameObject create(double x, double y) {
        return create(x, y, 0);
    }

    public static GameObject create(double x, double y, int z) {
        return new GameObject(List.of(new Position(x, y, z)));
    }

    public static GameObject create(double x, double y, int z, double directionDraw) {
        GameObject gameObject = create(x, y, z);
        gameObject.getComponent(Position.class).setDirectionDraw(directionDraw);

        return gameObject;
    }

    public static GameObject create(double x, double y, int z, double directionDraw, Texture texture) {
        GameObject gameObject = create(x, y, z, directionDraw);
        gameObject.setComponent(new SpriteRender(texture));

        return gameObject;
    }

    public static GameObject create(double x, double y, int z, double directionDraw, List<Texture> textures) {
        GameObject gameObject = create(x, y, z, directionDraw);
        gameObject.setComponent(new AnimationRender(textures));

        return gameObject;
    }

    public static GameObject create(Component component) {
        GameObject gameObject = create(0, 0);
        gameObject.setComponent(component);
        return gameObject;
    }
}
