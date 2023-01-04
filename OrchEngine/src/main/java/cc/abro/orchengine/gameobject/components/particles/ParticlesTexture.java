package cc.abro.orchengine.gameobject.components.particles;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.util.Vector2;
import org.lwjgl.opengl.GL11;

public class ParticlesTexture extends Particles {

    @Override
    public void draw() {
        GL11.glLoadIdentity();

        for (Part part : parts) {
            part.color.bind();
            part.texture.bind();
            Vector2<Integer> relativePosition = Context.getService(LocationManager.class)
                    .getActiveLocation()
                    .getCamera()
                    .toRelativePosition(new Vector2<>((int) part.x, (int) part.y));

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
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f((float) (defaultX - part.width / 2), (float) (defaultY - part.height / 2));
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f((float) (defaultX + part.width / 2), (float) (defaultY - part.height / 2));
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f((float) (defaultX + part.width / 2), (float) (defaultY + part.height / 2));
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f((float) (defaultX - part.width / 2), (float) (defaultY + part.height / 2));
            GL11.glEnd();
            part.texture.unbind();
        }
    }

}
