package tow.engine.obj;

import tow.engine.image.TextureHandler;
import tow.engine.obj.components.Position;
import tow.engine.obj.components.render.Animation;
import tow.engine.obj.components.render.Sprite;

import java.util.Arrays;

public class ObjFactory {

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
        gameObject.setComponent(new Sprite(textureHandler));

        return gameObject;
    }

    public static GameObject create(double x, double y, double directionDraw, TextureHandler textureHandler){
        return create(x, y, textureHandler.depth, directionDraw, textureHandler);
    }

    public static GameObject create(double x, double y, int depth, double directionDraw, TextureHandler[] textureHandler){
        GameObject gameObject = create(x, y, depth, directionDraw);
        gameObject.setComponent(new Animation(textureHandler));

        return gameObject;
    }

    public static GameObject create(double x, double y, double directionDraw, TextureHandler[] textureHandler){
        return create(x, y, textureHandler[0].depth, directionDraw, textureHandler);
    }
}
