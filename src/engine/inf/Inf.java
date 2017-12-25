package engine.inf;

import engine.image.TextureHandler;
import engine.inf.frame.ColorFrame;
import engine.inf.frame.Frame;
import engine.io.KeyboardHandler;
import engine.obj.Obj;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class Inf  {

    public Obj obj;

    public boolean delete = false;
    public Frame frame;

    public Inf(int x, int y, int width, int height, TextureHandler texture){
        obj = new Obj(x, y, -999, 90, texture);
        obj.position.absolute = false;
        obj.rendering.setWidth(width);
        obj.rendering.setHeight(height);
    }

    public void update(){}

    public void draw(){
        if (KeyboardHandler.isKeyDown(Keyboard.KEY_E)) frame = new ColorFrame(Color.black);
        obj.draw();
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
