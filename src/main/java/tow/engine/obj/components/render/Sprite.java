package tow.engine.obj.components.render;

import tow.engine.Vector2;
import tow.engine.obj.components.Position;
import tow.engine.resources.textures.Texture;
import tow.engine.image.TextureHandler;
import tow.engine.obj.Obj;
import org.lwjgl.opengl.GL11;

public class Sprite extends Rendering {
	
    private TextureHandler textureHandler;

    public Sprite(TextureHandler textureHandler) {
		this.textureHandler = textureHandler;
    }

    @Override
    public void draw() {
        Vector2<Integer> relativePosition = getObj().getComponent(Position.class).getRelativePosition();
        double xView = relativePosition.x;
        double yView = relativePosition.y;
        double directionDraw = getObj().getComponent(Position.class).getDirectionDraw();

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