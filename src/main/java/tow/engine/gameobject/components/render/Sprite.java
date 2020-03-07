package tow.engine.gameobject.components.render;

import tow.engine.Vector2;
import tow.engine.gameobject.components.Position;
import tow.engine.resources.textures.Texture;
import tow.engine.image.TextureHandler;
import org.lwjgl.opengl.GL11;

public class Sprite extends Rendering {
	
    private TextureHandler textureHandler;

    public Sprite(TextureHandler textureHandler) {
		this.textureHandler = textureHandler;
    }

    @Override
    public void updateComponent(long delta){ }

    @Override
    protected void drawComponent() {
        Vector2<Integer> relativePosition = getGameObject().getComponent(Position.class).getRelativePosition();
        double xView = relativePosition.x;
        double yView = relativePosition.y;
        double directionDraw = getGameObject().getComponent(Position.class).getDirectionDraw();

        directionDraw -= 90; //смещена начального угла с Востока на Север

        int width = getWidth();
        int height = getHeight();

        GL11.glLoadIdentity();
        GL11.glTranslatef((float) xView, (float) yView, 0);
        GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);

        color.bind();
        textureHandler.texture.bind();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(-width/2, -height/2);
        GL11.glTexCoord2f(1,0);
        GL11.glVertex2f(width/2, -height/2);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex2f(width/2, height/2);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex2f(-width/2, height/2);
        GL11.glEnd();

        Texture.unbind();
    }

    @Override
    public void destroy() { }

    @Override
    public int getWidthTexture(){
        return textureHandler.getWidth();
    }

    @Override
    public int getHeightTexture(){
        return textureHandler.getHeight();
    }

    @Override
    public int getWidth() {
        return (int) (getWidthTexture()*scale_x);
    }

    @Override
    public int getHeight() {
        return (int) (getHeightTexture()*scale_y);
    }

    @Override
    public void setWidth(int width) {
        scale_x = width/getWidth();
    }

    @Override
    public void setHeight(int height) {
        scale_y = height/getHeight();
    }

    @Override
    public void setDefaultSize(){
        scale_x = 1;
        scale_y = 1;
    }
}