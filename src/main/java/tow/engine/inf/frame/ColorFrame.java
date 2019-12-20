package tow.engine.inf.frame;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class ColorFrame implements Frame {

    public Color c;

    public ColorFrame(Color c){
        this.c = c;
    }

    @Override
    public void draw(int x, int y, int width, int height) {
        GL11.glLoadIdentity();
        GL11.glTranslatef(0, 0, 0);
        GL11.glColor3b((byte) (c.getRedByte()+128), (byte) (c.getGreenByte()+128), (byte) (c.getBlueByte()+128));

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBegin(GL11.GL_LINE_LOOP);
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
}
