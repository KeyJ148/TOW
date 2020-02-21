package tow.engine.obj;

import tow.engine.image.TextureHandler;
import tow.engine.obj.components.Position;
import tow.engine.obj.components.render.Animation;
import tow.engine.obj.components.render.Sprite;

import java.util.Arrays;

public class ObjFactory {

    public static Obj create(double x, double y, int depth){
        return new Obj(Arrays.asList(new Position(x, y, depth)));
    }

    public static Obj create(double x, double y, int depth, double directionDraw){
        Obj obj = create(x, y, depth);
        obj.getComponent(Position.class).setDirectionDraw(directionDraw);

        return obj;
    }

    public static Obj create(double x, double y, int depth, double directionDraw, TextureHandler textureHandler){
        Obj obj = create(x, y, depth, directionDraw);
        obj.setComponent(new Sprite(textureHandler));

        return obj;
    }

    public static Obj create(double x, double y, double directionDraw, TextureHandler textureHandler){
        return create(x, y, textureHandler.depth, directionDraw, textureHandler);
    }

    public static Obj create(double x, double y, int depth, double directionDraw, TextureHandler[] textureHandler){
        Obj obj = create(x, y, depth, directionDraw);
        obj.setComponent(new Animation(textureHandler));

        return obj;
    }

    public static Obj create(double x, double y, double directionDraw, TextureHandler[] textureHandler){
        return create(x, y, textureHandler[0].depth, directionDraw, textureHandler);
    }
}
