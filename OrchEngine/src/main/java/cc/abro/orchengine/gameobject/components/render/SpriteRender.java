package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import org.lwjgl.opengl.GL11;

public class SpriteRender extends Rendering {

    @Getter
    private final Texture texture;

    public SpriteRender(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void draw() {
        Vector2<Double> relativePosition = getGameObject().getRelativePosition();
        double xView = relativePosition.x;
        double yView = relativePosition.y;
        double directionDraw = getGameObject().getDirection();

        directionDraw -= 90; //смещена начального угла с Востока на Север

        int width = getWidth();
        int height = getHeight();

        GL11.glLoadIdentity();
        GL11.glTranslatef((float) xView, (float) yView, 0);
        GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);

        getColor().bind();
        getOpenGlService().renderTextureGlQuadsFromCenter(width, height, texture);
    }
}