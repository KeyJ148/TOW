package tow.engine2.image;

import org.lwjgl.opengl.GL11;

public class Color extends java.awt.Color {

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
