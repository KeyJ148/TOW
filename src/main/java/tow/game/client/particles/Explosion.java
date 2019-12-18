package tow.game.client.particles;

import tow.engine.obj.Obj;
import tow.engine.obj.components.particles.Part;
import tow.engine.obj.components.particles.ParticlesTexture;
import org.newdawn.slick.Color;

import java.util.Random;

public class Explosion extends ParticlesTexture {

    private double size;

    public Explosion(Obj obj, double size){
        super(obj);
        this.size = size;
        double count = size*2;
        Random rand = new Random();
        if(size>70) count = size*3;
        for (int i=0; i<count; i++){
            Part part = new Part();
            part.textureHandler = engine.image.TextureManager.getTexture("part_ball");
            part.x = getObj().position.x;
            part.y = getObj().position.y;
            part.direction = getObj().position.getDirectionDraw() + rand.nextInt(360);
            part.speed = (size/5 + rand.nextInt((int) (2*size/5)));
            if(size<70){
                part.width = size/2 + rand.nextInt( (int) size/5);
                part.height = size/2 + rand.nextInt((int) size/5);
            }
            else {
                part.width = 70 / 2 + rand.nextInt((int) 70 / 5);
                part.height = 70 / 2 + rand.nextInt((int) 70 / 5);
            }
            if(part.speed > (2*size/5)) {
                part.color = new Color((int) 200 + rand.nextInt(55), (int) rand.nextInt(100), rand.nextInt(20));
                part.life = (size/400);
                part.life += (float) rand.nextInt(50) / 75;
            } else {
                part.color = new Color((int) (200 + rand.nextInt(55)), (int) (200 + rand.nextInt(55)), (int) (255));
                part.life = (size/200);
                part.life += (float) rand.nextInt(50) / 150;
            }
            part.type = Part.Type.FILL;
            parts.add(part);
        }
    }

    @Override
    public void updateChild(long delta, Part part) {
        if(part.speed > (2*size/5)) part.speed -= (float) (40*delta)/1000000000;
        part.color = new Color((float) (part.color.r - ((double) ((1 + part.speed/150)*delta)/1000000000/12)),
                               (float) (part.color.g  - ((double) ((1 + part.speed/150)*delta)/1000000000/4)),
                               (float) (part.color.b - (((double) (4*delta))/1000000000/2)),
                               (float) part.life*10);
    }
}