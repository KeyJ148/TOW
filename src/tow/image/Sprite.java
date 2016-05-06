package tow.image;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Sprite implements Rendering{
	
    private TextureHandler textureHandler;
    private Color color = Color.white;
    private int scale_x = 1;
    private int scale_y = 1;
    
    public Sprite(TextureHandler textureHandler) {
    	System.out.println(textureHandler.path);
		this.textureHandler = textureHandler;
    }
    
    public int getWidth() {
        return textureHandler.getWidth();
    }

    public int getHeight() {
        return textureHandler.getHeight();
    }
    
    public Mask getMask(){
		return textureHandler.mask;
	}
    
    public String getPath(){
		return textureHandler.path;
	}
    
    public void update(long delta){}//Наследуется от Rendering, нужен для анимации, вызывается каждый степ
    
    public void draw(int x, int y, double direction) {
    	direction -= Math.PI/2; //смещена начального угла с Востока на Север
    	direction = Math.toDegrees(direction);
    	
    	int width=(int)(getWidth()*scale_x); 
        int height=(int)(getHeight()*scale_y); 
    	
        GL11.glLoadIdentity();     
	    GL11.glTranslatef(x, y, 0);
	    GL11.glRotatef(Math.round(-direction), 0f, 0f, 1f);
	    
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
    }
    
    public void setColor(Color color){
    	this.color = color;
    }
    
    public boolean isAnim(){
    	return false;
    }
}