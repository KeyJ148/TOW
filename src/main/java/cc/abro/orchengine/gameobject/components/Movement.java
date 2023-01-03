package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.location.LocationManager;

import java.util.Arrays;
import java.util.List;

public class Movement extends Component {
    public double speed; //На сколько пикселей объект смещается за 1 секунду
    private double direction; //0, 360 - в право, против часовой - движение

    private double xPrevious;//коры объекта в предыдущем шаге
    private double yPrevious;//(для столкновения)
    private double directionPrevious;//директион объекта в предыдущем шаге (для столкновения)

    public boolean directionDrawEquals = true;//Угол обзора объекта равен углу поворота

    public Movement() {
        this(0, 90);
    }

    public Movement(double speed, double direction) {
        this.speed = speed;
        setDirection(direction);
    }

    @Override
    public void update(long delta) {
        xPrevious = getGameObject().getComponent(Position.class).x;
        yPrevious = getGameObject().getComponent(Position.class).y;
        directionPrevious = direction;

        getGameObject().getComponent(Position.class).x = getGameObject().getComponent(Position.class).x + speed * Math.cos(Math.toRadians(direction)) * ((double) delta / 1000000000);
        getGameObject().getComponent(Position.class).y = getGameObject().getComponent(Position.class).y - speed * Math.sin(Math.toRadians(direction)) * ((double) delta / 1000000000);
        Context.getService(LocationManager.class).getActiveLocation().checkGameObjectChunkChanged(getGameObject());

        if (directionDrawEquals) getGameObject().getComponent(Position.class).setDirectionDraw(direction);
    }

    @Override
    public void draw() {
    }

    public double getDirection() {
        if (direction % 360 >= 0) {
            return direction % 360;
        } else {
            return 360 - Math.abs(direction % 360);
        }
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getDirectionPrevious() {
        if (directionPrevious % 360 >= 0) {
            return directionPrevious % 360;
        } else {
            return 360 - Math.abs(directionPrevious % 360);
        }
    }

    public double getXPrevious() {
        return xPrevious;
    }

    public double getYPrevious() {
        return yPrevious;
    }

    @Override
    public Class getComponentClass() {
        return Movement.class;
    }

    @Override
    public List<Class<? extends Component>> getPreliminaryUpdateComponents() {
        return Arrays.asList();
    }

    @Override
    public List<Class<? extends Component>> getPreliminaryDrawComponents() {
        return Arrays.asList();
    }
}
