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
    private int frameNumber=0; //���-�� ������ [1;inf)
    private int frameSpeed; //����� ������� ��������� update ������ ����
    private int frameNow; //����� �������� ����� [0;inf)
    private int update; //������� ������ ��������� update � ��������� ����� �����
    public String path;//���� � �����, ���� ��� �������� ����� � �������
    public Mask mask;
    
    public Animation(String path, int frameSpeed, int frameNumber) {
		this.path = path; //��� �������� ����� � �������
		this.frameSpeed = frameSpeed;
		this.frameNumber = frameNumber;
		this.image = new Image[frameNumber];
		String urlStr;
		URL url;
		//�������� �����������
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
    	direction-=Math.PI/2; //������� ���������� ���� � ������� �� �����
		AffineTransform at = new AffineTransform(); 
		at.rotate(-direction,x+getWidth(frameNow)/2,y+getHeight(frameNow)/2); //�������� ���������� � ���������
        g.setTransform(at); //��� �������� ������� �� direction
        g.drawImage(image[frameNow], x, y, null);//��� ��������� ������� ����� ������� ����� ����
    }
    
    public BufferedImage[] toBufferedImage(){
    	BufferedImage[] bfArray= new BufferedImage[image.length]; 
    	for (int i=0;i<image.length; i++){
    		bfArray[i] = toBufferedImage(image[i]);
    	}
    	return bfArray;
    }
    
    private BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
    
    private BufferedImage setColorBufferedImage(BufferedImage image, Color setColor) {
        int width = getWidth();
        int height = getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	
                Color originalColor = new Color(image.getRGB(x, y), true);//true ���������� ������������� ���������� alpha-�����
                int red = originalColor.getRed();
                int green = originalColor.getGreen();
                int blue = originalColor.getBlue();
                int alpha = originalColor.getAlpha();
                
                int newRed = setColor.getRed() * red/255;
                int newGreen = setColor.getGreen() * green/255;
                int newBlue = setColor.getBlue() * blue/255;
                Color newColor = new Color(newRed, newGreen, newBlue, alpha);
                image.setRGB(x, y, newColor.getRGB());
                
            }
        }
        return image;
    }
    
    public void setColor(Color c){
    	Image[] newImage = new Image[image.length]; 
    	BufferedImage bi;
	    for (int i=0;i<image.length; i++){
	    	bi = toBufferedImage(image[i]);
	    	bi = setColorBufferedImage(bi, c);
	    	newImage[i] = Toolkit.getDefaultToolkit().createImage(bi.getSource());
	    }
	    this.image = newImage;
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
	
	public String getPath(){
		return path;
	}
	
	public boolean isAnim(){
    	return true;
    }
}