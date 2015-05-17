package main.image;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.imageio.*;

import main.Global;

import java.io.*;
import java.net.URL;

public class Animation implements Cloneable, Rendering{
    private Image[] image;
    private int frameNumber=0; // ол-во кадров [1;inf)
    private int frameSpeed; //„ерез сколько интераций update мен€ть кадр
    private int frameNow; //Ќомер текущего кадра [0;inf)
    private int update; //—колько прошло интераций update с последней смены кадра
    public String path;//путь к файлу, нужн дл€ создани€ маски и консоли
    public Mask mask;
    
    public Animation(String path, int frameSpeed, int frameNumber) {
		this.path = path; //дл€ создани€ маски и консоли
		this.frameSpeed = frameSpeed;
		this.frameNumber = frameNumber;
		this.image = new Image[frameNumber];
		String urlStr;
		URL url;
		//«агрузка изображений
		for(int i=0;i<frameNumber;i++){
			BufferedImage sourceImage = null;
			urlStr = path + "/" + (i+1) + ".png";
			try {
				url = this.getClass().getClassLoader().getResource(urlStr);
				sourceImage = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
			image[i] = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
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
		this.update = 0;
        this.frameSpeed = frameSpeed;
    }
    
    public int getFrameNumber() {
        return frameNumber;
    }
    
    public int getWidth(int frame) {
        return image[frame].getWidth(null);
    }

    public int getHeight(int frame) {
        return image[frame].getHeight(null);
    }
    
    public Mask getMask(){
		return this.mask;
	}
	
	public Animation clone() throws CloneNotSupportedException {
		return (Animation)super.clone();
	}
    
    public void update() {
		this.update++;
		if (this.update == this.frameSpeed) {
			this.update = 0;
			if (this.frameNow == this.frameNumber - 1) {
				this.frameNow = 0;
			} else {
				this.frameNow++;
			}
		}
	}
    
    public void draw(Graphics2D g,int x,int y,double direction) {
		direction-=Math.PI/2; //смещена начального угла с ¬остока на —евер
		AffineTransform at = new AffineTransform(); 
		at.rotate(-direction,x+getWidth(frameNow)/2,y+getHeight(frameNow)/2); //—оздание трансформа с поворотом
        g.setTransform(at); //дл€ поворота спрайта на direction
        g.drawImage(image[frameNow], x, y, null);//дл€ отрисовки спрайта нужен верхний левый угол
    }

	public int getWidth() {
		return getWidth(getFrameNow());
	}

	public int getHeight() {
		return getHeight(getFrameNow());
	}

	public Image getImage() {
		return image[frameNow];
	}
}