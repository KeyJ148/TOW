package cc.abro.orchengine.gameobject.components.particles;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.util.Vector2;
import org.lwjgl.opengl.GL11;

public class ParticlesGeometry<T extends GameObject> extends Particles<T> {

    public ParticlesGeometry(int z) {
        super(z);
    }

    @Override
    public void draw() {
        GL11.glLoadIdentity();

        for (Part part : parts) {
            part.color.bind();
            Vector2<Double> relativePosition = getLocationManager()
                    .getActiveLocation()
                    .getCamera()
                    .toRelativePosition(new Vector2<>(part.x, part.y));

            double defaultX, defaultY;
            if (rotate) {
                GL11.glLoadIdentity();
                GL11.glTranslatef(Math.round(relativePosition.x), Math.round(relativePosition.y), 0);
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
            GL11.glVertex2f((float) (defaultX - part.width / 2), (float) (defaultY - part.height / 2));
            GL11.glVertex2f((float) (defaultX + part.width / 2), (float) (defaultY - part.height / 2));
            GL11.glVertex2f((float) (defaultX + part.width / 2), (float) (defaultY + part.height / 2));
            GL11.glVertex2f((float) (defaultX - part.width / 2), (float) (defaultY + part.height / 2));
            GL11.glEnd();
        }
    }
}
