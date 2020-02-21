package tow.engine.obj.components;

import tow.engine.Global;
import tow.engine.obj.Component;
import tow.engine.obj.Obj;

public class Movement extends Component {
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
    public void update(long delta){
        xPrevious = getObj().getComponent(Position.class).x;
        yPrevious = getObj().getComponent(Position.class).y;
        directionPrevious = direction;

        getObj().getComponent(Position.class).x = getObj().getComponent(Position.class).x + speed * Math.cos(Math.toRadians(direction)) * ((double) delta/1000000000);
        getObj().getComponent(Position.class).y = getObj().getComponent(Position.class).y - speed * Math.sin(Math.toRadians(direction)) * ((double) delta/1000000000);
        Global.location.mapControl.update(getObj());

        if (directionDrawEquals) getObj().getComponent(Position.class).setDirectionDraw(direction);
    }

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
    public Class getComponentClass() {
        return Movement.class;
    }
}
