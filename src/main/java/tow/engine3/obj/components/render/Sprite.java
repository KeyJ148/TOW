package tow.engine3.obj.components.render;

import tow.engine.Vector2;
import tow.engine2.image.Texture;
import tow.engine3.image.TextureHandler;
import tow.engine3.obj.Obj;
import org.lwjgl.opengl.GL11;

public class Sprite extends Rendering {
	
    private TextureHandler textureHandler;

    public Sprite(Obj obj, TextureHandler textureHandler) {
        super(obj);
		this.textureHandler = textureHandler;
    }

    @Override
    public void draw() {
        Vector2<Integer> relativePosition = getObj().position.getRelativePosition();
        double xView = relativePosition.x;
        double yView = relativePosition.y;
        double directionDraw = getObj().position.getDirectionDraw();

        directionDraw -= 90; //смещена начального угла с Востока на Север
    	
    	int width=(int)(getWidthTexture()*scale_x);
        int height=(int)(getHeightTexture()*scale_y);

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