package tow.engine.obj.components.particles;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.resources.textures.Texture;
import tow.engine.obj.Obj;
import org.lwjgl.opengl.GL11;

public class ParticlesTexture extends Particles {

    @Override
    public void destroy() { }

    @Override
    protected void drawComponent() {
        GL11.glLoadIdentity();

        for (Part part : parts){
            part.color.bind();
            part.textureHandler.texture.bind();
            Vector2<Integer> relativePosition = Global.location.camera.toRelativePosition(new Vector2((int) part.x, (int) part.y));

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
            Texture.unbind();
        }
    }

}
