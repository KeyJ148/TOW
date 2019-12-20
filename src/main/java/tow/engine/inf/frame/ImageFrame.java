package tow.engine.inf.frame;

import tow.engine.image.TextureHandler;
import tow.engine.obj.Obj;


public class ImageFrame implements Frame {

    public Obj degree;
    public Obj wall;

    public ImageFrame(TextureHandler degree, TextureHandler wall){
        this.degree = new Obj(0, 0, 0, 0, degree);
        this.wall = new Obj(0, 0, 0, 0, wall);
    }

    @Override
    public void draw(int x, int y, int width, int height) {
        drawWall(x, y, width, height);
        drawDegree(x, y, width, height);
    }

    //Отрисовка боковых граней
    protected void drawWall(int x, int y, int width, int height){
        wall.position.x = x;
        wall.position.y = y-height/2;
        wall.position.setDirectionDraw(90);
        wall.rendering.scale_x = width/2;
        wall.rendering.draw();

        wall.position.x = x;
        wall.position.y = y+height/2;
        wall.position.setDirectionDraw(90);
        wall.rendering.scale_x = width/2;
        wall.rendering.draw();

        wall.position.x = x-width/2;
        wall.position.y = y;
        wall.position.setDirectionDraw(0);
        wall.rendering.scale_x = height/2;
        wall.rendering.draw();

        wall.position.x = x+width/2;
        wall.position.y = y;
        wall.position.setDirectionDraw(0);
        wall.rendering.scale_x = height/2;
        wall.rendering.draw();
    }

    //Отрисовка углов
    private void drawDegree(int x, int y, int width, int height){
        degree.position.x = x-width/2+degree.rendering.getWidth()/4;
        degree.position.y = y-height/2+degree.rendering.getHeight()/4;
        degree.position.setDirectionDraw(90);
        degree.rendering.draw();

        degree.position.x = x+width/2-degree.rendering.getWidth()/4;
        degree.position.y = y-height/2+degree.rendering.getHeight()/4;
        degree.position.setDirectionDraw(0);
        degree.rendering.draw();

        degree.position.x = x+width/2-degree.rendering.getWidth()/4;
        degree.position.y = y+height/2-degree.rendering.getHeight()/4;
        degree.position.setDirectionDraw(270);
        degree.rendering.draw();

        degree.position.x = x-width/2+degree.rendering.getWidth()/4;
        degree.position.y = y+height/2-degree.rendering.getHeight()/4;
        degree.position.setDirectionDraw(180);
        degree.rendering.draw();
    }
}
