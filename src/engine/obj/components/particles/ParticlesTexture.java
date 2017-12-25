package engine.obj.components.particles;

import engine.obj.Obj;
import org.lwjgl.opengl.GL11;

public class ParticlesTexture extends Particles {

    public ParticlesTexture(Obj obj){
        super(obj);
    }

    @Override
    public void draw(){
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        for (Part part : parts){
            part.color.bind();
            part.textureHandler.texture.bind();

            double defaultX, defaultY;
            if (rotate) {
                GL11.glLoadIdentity();
                GL11.glTranslatef((float) part.x, (float) part.y, 0);
                GL11.glRotatef(Math.round(-part.directionDraw), 0f, 0f, 1f);
                defaultX = 0;
                defaultY = 0;
            } else {
                defaultX = part.x;
                defaultY = part.y;
            }

            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0,0);
                GL11.glVertex2f((float) (defaultX-part.width/2), (float) (defaultY-part.height/2));
                GL11.glTexCoord2f(1,0);
                GL11.glVertex2f((float) (defaultX+part.width/2), (float) (defaultY-part.height/2));
                GL11.glTexCoord2f(1,1);
                GL11.glVertex2f((float) (defaultX+part.width/2), (float) (defaultY+part.height/2));
                GL11.glTexCoord2f(0,1);
                GL11.glVertex2f((float) (defaultX-part.width/2), (float) (defaultY+part.height/2));
            GL11.glEnd();
        }
    }

}
