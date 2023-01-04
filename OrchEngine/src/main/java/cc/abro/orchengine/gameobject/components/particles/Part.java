package cc.abro.orchengine.gameobject.components.particles;

import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.textures.Texture;

public class Part {

    public enum Type {FILL, HOLLOW}

    public Type type;

    public Texture texture;

    public Color color;
    public double x, y;
    public double speed;
    public double direction;
    public double directionDraw;
    public double width;
    public double height;
    public double life;//Время жизни в секундах

    public Part() {
        this(0, 0, 0, 0, 0, 0, 0, 0);
    }

    public Part(int x, int y, double life, double speed, double direction, int width, int height, Texture texture) {
        this(x, y, life, speed, direction, direction, width, height);
        this.texture = texture;
    }

    public Part(int x, int y, double life, double speed, double direction, double directionDraw, int width, int height, Texture texture) {
        this(x, y, life, speed, direction, directionDraw, width, height);
        this.texture = texture;
    }

    public Part(int x, int y, double life, double speed, double direction, double directionDraw, int width, int height) {
        this(x, y, life, speed, direction, directionDraw, width, height, Type.FILL);
    }

    public Part(int x, int y, double life, double speed, double direction, double directionDraw, int width, int height, Type type) {
        this(x, y, life, speed, direction, directionDraw, width, height, new Color(Color.WHITE), type);
    }

    public Part(int x, int y, double life, double speed, double direction, double directionDraw, int width, int height, Color color, Type type) {
        this.x = x;
        this.y = y;
        this.life = life;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.directionDraw = directionDraw;
        this.color = color;
        this.type = type;
    }
}