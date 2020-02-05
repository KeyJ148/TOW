package tow.engine.obj.components.particles;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.obj.Obj;
import org.lwjgl.opengl.GL11;

public class ParticlesGeometry extends Particles {

    public ParticlesGeometry(Obj obj){
        super(obj);
    }

    @Override
    public void draw(){
        GL11.glLoadIdentity();

        for (Part part : parts){
            part.color.bind();
            Vector2<Integer> relativePosition = Global.room.camera.toRelativePosition(new Vector2((int) part.x, (int) part.y));

            double defaultX, defaultY;
            if (rotate) {
                GL11.glLoadIdentity();
                GL11.glTranslatef((float) relativePosition.x, (float) relativePosition.y, 0);
                GL11.glRotatef(Math.round(-part.directionDraw), 0f, 0f, 1f);
                defaultX = 0;
                defaultY = 0;
            } else {
                defaultX = relativePosition.x;
                defaultY = relativePosition.y;
            }

            int glBeginType = GL11.GL_QUADS;
            if (part.type.equals(Part.Type.FILL)) glBeginType = GL11.GL_QUADS;
            if (part.type.equals(Part.Type.HOLLOW)) glBeginType = GL11.GL_LINE_LOOP;

            GL11.glBegin(glBeginType);
                GL11.glVertex2f((float) (defaultX-part.width/2), (float) (defaultY-part.height/2));
                GL11.glVertex2f((float) (defaultX+part.width/2), (float) (defaultY-part.height/2));
                GL11.glVertex2f((float) (defaultX+part.width/2), (float) (defaultY+part.height/2));
                GL11.glVertex2f((float) (defaultX-part.width/2), (float) (defaultY+part.height/2));
            GL11.glEnd();
        }
    }
}
