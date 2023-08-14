package cc.abro.tow.client.particles;

import cc.abro.orchengine.gameobject.components.particles.Part;
import cc.abro.orchengine.gameobject.components.particles.ParticlesGeometry;
import cc.abro.orchengine.image.Color;

public class Pixel extends ParticlesGeometry {

    public Pixel(double x, double y, double dir, double speed, int z) {
        this(x, y, dir, speed, Color.BLACK, z);
    }

    public Pixel(double x, double y, double dir, double speed, Color color, int z) {
        super(z);
        Part part = new Part();

        part.x = x;
        part.y = y;
        part.direction = dir;
        part.speed = speed;

        part.directionDraw = 0;
        part.width = 1;
        part.height = 1;
        part.color = color;
        part.type = Part.Type.FILL;
        part.life = Integer.MAX_VALUE;

        parts.add(part);
    }
}
