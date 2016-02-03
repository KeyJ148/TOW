package main.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Global;

public class Animation implements Cloneable, Rendering{
    private Image[] image;
    private int frameNumber=0; // ол-во кадров [1;inf)
    private int frameSpeed; // ол-во кадров в секнду
    private int frameNow; //Ќомер текущего кадра [0;inf)
    private long update; //—колько прошло наносекунд с последней смены кадра
    public String path;//путь к файлу, нужн дл€ создани€ маски и консоли
    public Mask mask;
    
    public Animation(String path, int frameSpeed, int frameNumber) {
		this.path = path; //дл€ создани€ маски и консоли
		this.frameSpeed = frameSpeed;
		this.frameNumber = frameNumber;
		this.image = new Image[frameNumber];
		String urlStr;
		//«агрузка изображений
		for(int i=0;i<frameNumber;i++){
			BufferedImage sourceImage = null;
			urlStr = path + "/" + (i+1) + ".png";
			try {
				sourceImage = ImageIO.read(new File(urlStr));
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
		return mask;
	}
	
	public Animation clone() throws CloneNotSupportedException {
		return (Animation)super.clone();
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
    
    public void draw(Graphics2D g,int x,int y,double direction) {
    	direction-=Math.PI/2; //смещена начального угла с ¬остока на —евер
		AffineTransform at = new AffineTransform(); 
		at.rotate(-direction,x+getWidth(frameNow)/2,y+getHeight(frameNow)/2); //—оздание трансформа с поворотом
        g.setTransform(at); //дл€ поворота спрайта на direction
        g.drawImage(image[frameNow], x, y, null);//дл€ отрисовки спрайта нужен верхний левый угол
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
            	
                Color originalColor = new Color(image.getRGB(x, y), true);//true показывает необходимость передавать alpha-канал
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