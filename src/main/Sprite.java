package main;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.imageio.*;

import java.io.*;
import java.net.*;

public class Sprite implements Cloneable {
    private Image image;
    public String path;//путь к файлу, нужен для создания маски и консоли
    public Mask mask;
    
    public Sprite(String path) {
		this.path = path; //для создания маски и консоли 
		//Загрузка изображения
		BufferedImage sourceImage = null;
        try {
			URL url = this.getClass().getClassLoader().getResource(path);
			sourceImage = ImageIO.read(url);
			if (Global.setting.DEBUG_CONSOLE_IMAGE) System.out.println("Load image \"" + path + "\" complited.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.image = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
		//Создание маски
		this.mask = new Mask(path, getWidth(), getHeight());
		
    }
    
    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }
    
    public Image getImage(){
		return image;
	}
    
    public Mask getMask(){
		return this.mask;
	}
	
	public Sprite clone() throws CloneNotSupportedException {
		return (Sprite) super.clone();
	}
    
    public void draw(Graphics2D g,int x,int y,double direction) {
		direction-=Math.PI/2; //смещена начального угла с Востока на Север
		AffineTransform at = new AffineTransform(); 
		at.rotate(-direction,x+getWidth()/2,y+getHeight()/2); //Создание трансформа с поворотом
        g.setTransform(at); //для поворота спрайта на direction
        g.drawImage(image, x, y, null);//для отрисовки спрайта нужен верхний левый угол
    }
}