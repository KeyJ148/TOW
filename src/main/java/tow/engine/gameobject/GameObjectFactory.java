package tow.engine.gameobject;

import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.AnimationRender;
import tow.engine.gameobject.components.render.SpriteRender;
import tow.engine.resources.textures.Texture;

import java.util.Arrays;
import java.util.List;

public class GameObjectFactory {

    public static GameObject create(double x, double y){
        return create(x, y, 0);
    }

    public static GameObject create(double x, double y, int depth){
        return new GameObject(Arrays.asList(new Position(x, y, depth)));
    }

    public static GameObject create(double x, double y, int depth, double directionDraw){
        GameObject gameObject = create(x, y, depth);
        gameObject.getComponent(Position.class).setDirectionDraw(directionDraw);

        return gameObject;
    }

    public static GameObject create(double x, double y, int depth, double directionDraw, Texture texture){
        GameObject gameObject = create(x, y, depth, directionDraw);
        gameObject.setComponent(new SpriteRender(texture));

        return gameObject;
    }

    public static GameObject create(double x, double y, int depth, double directionDraw, List<Texture> textures){
        GameObject gameObject = create(x, y, depth, directionDraw);
        gameObject.setComponent(new AnimationRender(textures));

        return gameObject;
    }
}
