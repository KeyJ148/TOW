package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.util.Vector2;
import org.lwjgl.opengl.GL11;

public class RepeatableSpriteRender extends SpriteRender {

    public RepeatableSpriteRender(Texture texture, int z) {
        this(texture, z, false);
    }

    public RepeatableSpriteRender(Texture texture, int z, boolean unsuitableObject) {
        super(texture, z, unsuitableObject);
    }

    @Override
    public void draw() {
        Vector2<Double> relativePosition = getGameObject().getRelativePosition(); //TODO заменить все Vector2<Double> на Vector2d из OpenGL, т.к. в них есть математические функции?
        double xView = relativePosition.x;
        double yView = relativePosition.y;
        double directionDraw = getGameObject().getDirection();

        directionDraw -= 90; //смещена начального угла с Востока на Север

        int width = getWidth();
        int height = getHeight();
        int countTexturesInWidth = width / getTexture().getWidth();
        int countTexturesInHeight = height / getTexture().getHeight();

        GL11.glLoadIdentity();
        GL11.glTranslatef(Math.round(xView), Math.round(yView), 0);
        GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);

        getColor().bind();
        getTextureService().bindTexture(getTexture());

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(-width / 2, -height / 2);
        GL11.glTexCoord2f(countTexturesInWidth, 0);
        GL11.glVertex2f(width / 2, -height / 2);
        GL11.glTexCoord2f(countTexturesInWidth, countTexturesInHeight);
        GL11.glVertex2f(width / 2, height / 2);
        GL11.glTexCoord2f(0, countTexturesInHeight);
        GL11.glVertex2f(-width / 2, height / 2);
        GL11.glEnd();

        getTextureService().unbindTexture();
    }
}