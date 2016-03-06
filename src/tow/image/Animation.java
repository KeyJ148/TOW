package tow.image;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import tow.Global;

public class Animation implements Rendering{
	
    private Texture[] texture;
    private int frameNumber=0; //Кол-во кадров [1;inf)
    private int frameSpeed; //Кол-во кадров в секнду
    private int frameNow; //Номер текущего кадра [0;inf)
    private int scale_x = 1;
    private int scale_y = 1; 
    private long update; //Сколько прошло наносекунд с последней смены кадра
    
    public String path;//путь к файлу, нужн для создания маски и консоли
    public Mask mask;
    
    public Animation(String path, int frameSpeed, int frameNumber) {
		this.path = path; //для создания маски и консоли
		this.frameSpeed = frameSpeed;
		this.frameNumber = frameNumber;
		this.texture = new Texture[frameNumber];
		String urlStr;
		//Загрузка изображений
		for(int i=0;i<frameNumber;i++){
			urlStr = path + "/" + (i+1) + ".png";
			try {
				texture[i] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(urlStr));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        this.update = 0;
        
        if (Global.setting.DEBUG_CONSOLE_IMAGE) System.out.println("Load animation \"" + path + "\" complited.");
        this.mask = new Mask(path + "/1.", getWidth(0), getHeight(0));
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
        return (int) texture[frame].getWidth();
    }

    public int getHeight(int frame) {
        return (int) texture[frame].getHeight();
    }
    
    public Mask getMask(){
		return mask;
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
    	
    	int width=(int)(texture[frameNow].getImageWidth()*scale_x); 
        int height=(int)(texture[frameNow].getImageHeight()*scale_y); 
    	
        GL11.glLoadIdentity();       
	    GL11.glTranslatef(x+getWidth(frameNow)/2, y+getHeight(frameNow)/2, 0);
	    GL11.glRotatef(Math.round(direction), 0f, 0f, 1f);
	    GL11.glTranslatef(-getWidth()/2, -getHeight()/2, 0);
	    
	    org.newdawn.slick.Color.white.bind(); 
	    texture[frameNow].bind();
	    
	    GL11.glBegin(GL11.GL_QUADS);
		    GL11.glTexCoord2f(0,0); 
		    GL11.glVertex2f(0,0); 
		    GL11.glTexCoord2f(1,0); 
		    GL11.glVertex2f(width, 0); 
		    GL11.glTexCoord2f(1,1); 
		    GL11.glVertex2f(width, height); 
		    GL11.glTexCoord2f(0,1); 
		    GL11.glVertex2f(0, height); 
	    GL11.glEnd();
    }

	public int getWidth() {
		return getWidth(getFrameNow());
	}

	public int getHeight() {
		return getHeight(getFrameNow());
	}
	
	public String getPath(){
		return path;
	}
	
	public boolean isAnim(){
    	return true;
    }
}