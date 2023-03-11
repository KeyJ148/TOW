package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import lombok.Getter;

public class Movement extends Component implements Updatable {
    public double speed; //На сколько пикселей объект смещается за 1 секунду
    private double direction; //0, 360 - в право, против часовой - движение

    @Getter
    private double xPrevious;//коры объекта в предыдущем шаге
    @Getter
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
        xPrevious = getGameObject().getX();
        yPrevious = getGameObject().getY();
        directionPrevious = direction;

        if (speed != 0) {
            double nextX = getGameObject().getX() + speed * Math.cos(Math.toRadians(direction)) * ((double) delta / 1000000000);
            double nextY = getGameObject().getY() - speed * Math.sin(Math.toRadians(direction)) * ((double) delta / 1000000000);
            getGameObject().setPosition(nextX, nextY);
        }

        if (directionDrawEquals) getGameObject().setDirection(direction);
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
}
