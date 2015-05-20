package main.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

public interface Rendering {
	
	public int getWidth();

    public int getHeight();
    
    public Image getImage();
    
    public Mask getMask();
	
    public void draw(Graphics2D g,int x,int y,double direction);
    
    public void draw(Graphics2D g,int x,int y,double direction, Color c);
    
    public boolean isAnim();
    
    public String getPath();
    
    public void update();

}
