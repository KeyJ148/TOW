package engine.inf.examples;

import engine.Global;
import engine.image.TextureHandler;
import engine.inf.Inf;
import engine.inf.title.Title;
import org.newdawn.slick.Color;

public class Label extends Inf {

    public String label = new String();
    public int limit;//Максимально кол-во символов
    //Свойства надписи
    public int size;
    public Color color = Title.defaultColor;
    public int font = Title.defaultFont;

    public int startTextX = 0, startTextY = 0; //Начало текста относительно верхнего левого угла спрайта

    public Label(int x, int y, int width, int height, TextureHandler texture){
        super(x, y, width, height, texture);
        size = (int) (height/1.3);
        limit = (int) (width/size*1.9);
    }

    @Override
    public void draw(){
        super.draw();
        String drawLabel = label;
        if (label.length() > limit) drawLabel = label.substring(0, limit-1) + "..";

        int titleX = (int) obj.position.x-obj.rendering.getWidth()/2+startTextX;
        int titleY = (int) obj.position.y-obj.rendering.getHeight()/2+startTextY;
        Global.engine.render.addTitle(new Title(titleX, titleY, drawLabel, color, size, font));
    }
}
