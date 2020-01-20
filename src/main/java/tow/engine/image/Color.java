package tow.engine.image;

import org.lwjgl.opengl.GL11;

public class Color extends java.awt.Color {

    public static Color BLACK = new Color(0, 0, 0);
    public static Color WHITE = new Color(255, 255, 255);
    public static Color GRAY = new Color(127, 127, 127);
    public static Color RED = new Color(255, 0, 0);
    public static Color GREEN = new Color(0, 255, 0);
    public static Color BLUE = new Color(0, 0, 255);

    public Color(int r, int g, int b) {
        super(r, g, b);
    }

    public Color(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public Color(float r, float g, float b, float a) {
        this((int) (255*r), (int) (255*g), (int) (255*b), (int) (255*a));
    }

    public Color(java.awt.Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public float getFloatRed(){
        return ((float) getRed())/255;
    }

    public float getFloatGreen(){
        return ((float) getGreen())/255;
    }

    public float getFloatBlue(){
        return ((float) getBlue())/255;
    }

    public float getFloatAlpha(){
        return ((float) getAlpha())/255;
    }

    public void bind(){
        GL11.glColor4f(getFloatRed(), getFloatGreen(), getFloatBlue(), getFloatAlpha());
    }
}
