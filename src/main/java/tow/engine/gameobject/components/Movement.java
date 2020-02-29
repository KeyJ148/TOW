package tow.engine.gameobject.components;

import tow.engine.Global;
import tow.engine.gameobject.QueueComponent;

import java.util.Arrays;
import java.util.List;

public class Movement extends QueueComponent {
    public double speed; //На сколько пикселей объект смещается за 1 секунду
    private double direction; //0, 360 - в право, против часовой - движение

    private double xPrevious;//коры объекта в предыдущем шаге
    private double yPrevious;//(для столкновения)
    private double directionPrevious;//директион объекта в предыдущем шаге (для столкновения)

    public boolean directionDrawEquals = true;//Угол обзора объекта равен углу поворота

    public Movement(){
        this(0, 90);
    }

    public Movement(double speed, double direction) {
        this.speed = speed;
        setDirection(direction);
    }

    @Override
    public void updateComponent(long delta){
        xPrevious = getGameObject().getComponent(Position.class).x;
        yPrevious = getGameObject().getComponent(Position.class).y;
        directionPrevious = direction;

        getGameObject().getComponent(Position.class).x = getGameObject().getComponent(Position.class).x + speed * Math.cos(Math.toRadians(direction)) * ((double) delta/1000000000);
        getGameObject().getComponent(Position.class).y = getGameObject().getComponent(Position.class).y - speed * Math.sin(Math.toRadians(direction)) * ((double) delta/1000000000);
        Global.location.mapControl.update(getGameObject());

        if (directionDrawEquals) getGameObject().getComponent(Position.class).setDirectionDraw(direction);
    }

    @Override
    protected void drawComponent() { }

    public double getDirection(){
        if (direction%360 >= 0){
            return direction%360;
        } else {
            return 360-Math.abs(direction%360);
        }
    }

    public void setDirection(double direction){
        this.direction = direction;
    }

    public double getDirectionPrevious(){
        if (directionPrevious%360 >= 0){
            return directionPrevious%360;
        } else {
            return 360-Math.abs(directionPrevious%360);
        }
    }

    public double getXPrevious(){
        return xPrevious;
    }

    public double getYPrevious() {
        return yPrevious;
    }

    @Override
    public void destroy() { }

    @Override
    public Class getComponentClass() {
        return Movement.class;
    }

    @Override
    public List<Class<? extends QueueComponent>> getComponentsUpdatePreviously() {
        return Arrays.asList();
    }

    @Override
    public List<Class<? extends QueueComponent>> getComponentsDrawPreviously() {
        return Arrays.asList();
    }
}
