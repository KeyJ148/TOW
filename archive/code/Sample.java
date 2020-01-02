package game.client;

import tow.engine3.Vector2;
import tow.engine2.obj.Obj;
import tow.engine2.obj.components.particles.Part;
import tow.engine2.obj.components.particles.ParticlesGeometry;
import org.newdawn.slick.Color;

import java.util.Random;

public class Sample extends ParticlesGeometry {

    public static final double maxLife = 1;
    public static final double partcount = 1024;
    public Sample(Obj obj){
        super(obj);
        destroyObject = true;
        rotate = true;

        Random rand = new Random();
        for (int i=0; i<partcount; i++){
            Part part = new Part();
            Vector2<Integer> relativePosition = getObj().position.getRelativePosition();

            part.direction = getObj().position.getDirectionDraw() + (i/16)*360/(partcount/16);
            part.width = 5;
            part.height = 5;
            part.directionDraw = part.direction-90;
            part.color = new Color(255, 255, 255);
            part.type = Part.Type.FILL;
            parts.add(part);
        }
    }

    @Override
    public void updateChild(long delta, Part part) {
        part.color = new Color((float) (part.color.r - ((double)delta)/1000000000/4),(float) (part.color.g - ((double)delta)/1000000000),(float) (part.color.b - ((double)8*delta)/1000000000));
        //if(part.speed >= 0 && part.speed <= 50) part.speed -= 200*((double)delta)/1000000000; else part.speed = 0;
    }

    private class ExplosionPart extends Part{

        public int type;

        public ExplosionPart(int type){
            this.type = type;
        }
    }
}