package tow.image;

import org.newdawn.slick.Color;

public interface Rendering {
	
	public int getWidth();

    public int getHeight();
    
    public Mask getMask();
	
    public void draw(int x,int y,double direction);
    
    public boolean isAnim();
    
    public String getPath();
    
    public void update(long delta);
    
    public void setColor(Color color);
}
