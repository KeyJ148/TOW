package tow.image;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import tow.Global;

public class Animation implements Rendering{
	
    private TextureHandler[] textureHandler;
    private int frameNumber = 0; //Кол-во кадров [1;inf)
    private int frameSpeed = 0; //Кол-во кадров в секнду
    private int frameNow; //Номер текущего кадра [0;inf)

    private Color color = Color.white;
    private int scale_x = 1;
    private int scale_y = 1; 
    
    private long update = 0; //Сколько прошло наносекунд с последней смены кадра
    
    public Animation(TextureHandler[] textureHandler) {
		this.textureHandler = textureHandler;
		this.frameNumber = textureHandler.length;
    }
    
    public int getFrameNow() {
        return frameNow;
    }
    
    public int getFrameSpeed() {
        return frameSpeed;
    }
    
    public void setFrameNow(int frameNow) {
        this.frameNow = frameNow;
    }
    
    public void setFrameSpeed(int frameSpeed) {
    	if (frameSpeed < 0){
    		Global.error("Frame speed must be >= 0");
    		return;
    	}
    	
		this.update = 0;
        this.frameSpeed = frameSpeed;
    }
    
    public int getFrameNumber() {
        return frameNumber;
    }
    
    public int getWidth(int frame) {
        return (int) textureHandler[frame].getWidth();
    }

    public int getHeight(int frame) {
        return (int) textureHandler[frame].getHeight();
    }
    
    public Mask getMask(){
		return textureHandler[0].mask;
	}
    
    public void update(long delta) {
		update += delta;
		if ((frameSpeed != 0) && (update > 1000000000/frameSpeed)) {
			update = 0;
			if (frameNow == frameNumber - 1) {
				frameNow = 0;
			} else {
				frameNow++;
			}
		}
	}
    
    public void draw(int x,int y,double direction) {
    	direction -= Math.PI/2; //смещена начального угла с Востока на Север
    	direction = Math.toDegrees(direction);
    	
    	int width=(int)(textureHandler[frameNow].getWidth()*scale_x); 
        int height=(int)(textureHandler[frameNow].getHeight()*scale_y); 
    	
        GL11.glLoadIdentity();     
	    GL11.glTranslatef(x, y, 0);
	    GL11.glRotatef(Math.round(-direction), 0f, 0f, 1f);
	    
	    color.bind(); 
	    textureHandler[frameNow].texture.bind();
	    
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

	public int getWidth() {
		return getWidth(getFrameNow());
	}

	public int getHeight() {
		return getHeight(getFrameNow());
	}
	
	public String getPath(){
		return textureHandler[frameNow].path;
	}
	
	public void setColor(Color color){
    	this.color = color;
    }
	
	public boolean isAnim(){
    	return true;
    }
}