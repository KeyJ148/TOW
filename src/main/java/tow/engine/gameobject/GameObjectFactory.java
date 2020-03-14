package tow.engine.gameobject;

import tow.engine.image.TextureHandler;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.AnimationRender;
import tow.engine.gameobject.components.render.SpriteRender;

import java.util.Arrays;

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

    public static GameObject create(double x, double y, int depth, double directionDraw, TextureHandler textureHandler){
        GameObject gameObject = create(x, y, depth, directionDraw);
        gameObject.setComponent(new SpriteRender(textureHandler));

        return gameObject;
    }

    public static GameObject create(double x, double y, double directionDraw, TextureHandler textureHandler){
        return create(x, y, textureHandler.depth, directionDraw, textureHandler);
    }

    public static GameObject create(double x, double y, int depth, double directionDraw, TextureHandler[] textureHandler){
        GameObject gameObject = create(x, y, depth, directionDraw);
        gameObject.setComponent(new AnimationRender(textureHandler));

        return gameObject;
    }

    public static GameObject create(double x, double y, double directionDraw, TextureHandler[] textureHandler){
        return create(x, y, textureHandler[0].depth, directionDraw, textureHandler);
    }
}
