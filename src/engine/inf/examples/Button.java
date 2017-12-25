package engine.inf.examples;

import engine.image.TextureHandler;
import engine.io.KeyboardHandler;
import engine.io.MouseHandler;

public class Button extends Label {

    public int hotKey;

    public Button(int x, int y, int width, int height, TextureHandler texture){
        super(x, y, width, height, texture);
    }

    @Override
    public void update(){
        if (KeyboardHandler.isKeyDown(hotKey)){
            KeyboardHandler.lock();
            action();
        }

        if (MouseHandler.mouseX > obj.position.x-obj.rendering.getWidth()/2 &&
            MouseHandler.mouseX < obj.position.x+obj.rendering.getWidth()/2 &&
            MouseHandler.mouseY > obj.position.y-obj.rendering.getHeight()/2 &&
            MouseHandler.mouseY < obj.position.y+obj.rendering.getHeight() &&
            MouseHandler.mouseDown1){
                MouseHandler.lock();
                action();
        }

    }

    public void action(){}

}
