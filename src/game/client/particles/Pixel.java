package game.client.particles;

import engine.obj.Obj;
import engine.obj.components.particles.Part;
import engine.obj.components.particles.ParticlesGeometry;
import org.newdawn.slick.Color;

public class Pixel extends ParticlesGeometry {

    public Pixel(Obj obj, double x, double y, double dir, double speed) {
        super(obj);

        Part part = new Part();

        part.x = x;
        part.y = y;
        part.direction = dir;
        part.speed = speed;

        part.directionDraw = 0;
        part.width = 1;
        part.height = 1;
        part.color = Color.black;
        part.type = Part.Type.FILL;
        part.life = Integer.MAX_VALUE;

        parts.add(part);

    }
}
