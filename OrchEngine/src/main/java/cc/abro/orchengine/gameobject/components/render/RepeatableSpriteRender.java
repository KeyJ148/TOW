package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.util.Vector2;
import org.lwjgl.opengl.GL11;

public class RepeatableSpriteRender extends SpriteRender {

    public RepeatableSpriteRender(Texture texture) {
        super(texture);
    }

    @Override
    public void draw() {
        Vector2<Integer> relativePosition = getGameObject().getComponent(Position.class).getRelativePosition();
        double xView = relativePosition.x;
        double yView = relativePosition.y;
        double directionDraw = getGameObject().getComponent(Position.class).getDirectionDraw();

        directionDraw -= 90; //смещена начального угла с Востока на Север

        int width = getWidth();
        int height = getHeight();
        int countTexturesInWidth = getWidth() / getWidthTexture();
        int countTexturesInHeight = getHeight() / getHeightTexture();

        GL11.glLoadIdentity();
        GL11.glTranslatef((float) xView, (float) yView, 0);
        GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);

        color.bind();
        texture.bind();

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

        texture.unbind();
    }
}