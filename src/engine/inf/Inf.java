package engine.inf;

import engine.image.TextureHandler;
import engine.image.TextureManager;
import engine.inf.frame.Frame;
import engine.obj.Obj;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Inf  {

    public Obj obj;
    public Color colorBackground;

    public boolean delete = false;
    public Frame frame;

    public Inf(int x, int y, int width, int height, TextureHandler texture){
        obj = new Obj(x, y, -999, 90, texture);
        obj.position.absolute = false;
        obj.rendering.setWidth(width);
        obj.rendering.setHeight(height);
    }

    public Inf(int x, int y, int width, int height, Color color){
        obj = new Obj(x, y, -999, 90, TextureManager.getTexture("sys_null"));
        obj.position.absolute = false;
        obj.rendering.setWidth(width);
        obj.rendering.setHeight(height);

        colorBackground = color;
    }

    public void update(){}

    public void draw(){
        obj.draw();

        if (colorBackground != null){
            int x = (int) obj.position.x;
            int y = (int) obj.position.y;
            int width = obj.rendering.getWidth();
            int height = obj.rendering.getHeight();

            GL11.glLoadIdentity();
            GL11.glTranslatef(0, 0, 0);
            GL11.glColor3b((byte) (colorBackground.getRedByte()+128),
                           (byte) (colorBackground.getGreenByte()+128),
                           (byte) (colorBackground.getBlueByte()+128));

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(x-width/2, y-height/2);
            GL11.glTexCoord2f(1,0);
            GL11.glVertex2f(x+width/2, y-height/2);
            GL11.glTexCoord2f(1,1);
            GL11.glVertex2f(x+width/2, y+height/2);
            GL11.glTexCoord2f(0,1);
            GL11.glVertex2f(x-width/2, y+height/2);
            GL11.glEnd();
        }

        if (frame != null){
            int x = (int) obj.position.x;
            int y = (int) obj.position.y;
            int width = obj.rendering.getWidth();
            int height = obj.rendering.getHeight();
            frame.draw(x, y, width, height);
        }
    }

    public void delete(){
        delete = true;
    }
}
