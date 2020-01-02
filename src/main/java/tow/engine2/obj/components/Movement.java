package tow.engine2.obj.components;

import tow.engine.Global;
import tow.engine2.obj.Obj;

public class Movement extends Component {
    public double speed; //На сколько пикселей объект смещается за 1 секунду
    private double direction; //0, 360 - в право, против часовой - движение

    private double xPrevious;//коры объекта в предыдущем шаге
    private double yPrevious;//(для столкновения)
    private double directionPrevious;//директион объекта в предыдущем шаге (для столкновения)

    public boolean directionDrawEquals = true;//Угол обзора объекта равен углу поворота

    public Movement(Obj obj){
        this(obj, 0, 90);
    }

    public Movement(Obj obj, double speed, double direction) {
        super(obj);
        this.speed = speed;
        setDirection(direction);
    }

    @Override
    public void update(long delta){
        xPrevious = getObj().position.x;
        yPrevious = getObj().position.y;
        directionPrevious = direction;

        getObj().position.x = getObj().position.x + speed * Math.cos(Math.toRadians(direction)) * ((double) delta/1000000000);
        getObj().position.y = getObj().position.y - speed * Math.sin(Math.toRadians(direction)) * ((double) delta/1000000000);
        Global.room.mapControl.update(getObj());

        if (directionDrawEquals) getObj().position.setDirectionDraw(direction);
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
}
