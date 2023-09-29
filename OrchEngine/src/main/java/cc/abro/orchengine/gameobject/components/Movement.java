package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import lombok.Getter;
import lombok.Setter;

public class Movement<T extends GameObject> extends PositionableComponent<T> implements Updatable {

    @Getter @Setter
    private double speed; //На сколько пикселей объект смещается за 1 секунду
    @Getter
    private double xPrevious;//коры объекта в предыдущем шаге
    @Getter
    private double yPrevious;//(для столкновения) //TODO мб в контроллер вынести или куда-то в компонент отвечающий за физику (не за коллизии), тогда можно будет убрать getPreliminaryUpdateComponents из TankMovementController, т.к. оно тупо для выставления Previous
    @Getter
    private double directionPrevious;//Угол объекта в предыдущем шаге (для столкновения)

    public Movement() {
        this(0);
    }

    public Movement(double speed) {
        this.speed = speed;
    }

    @Override
    public void initialize() {
        setPreviousData();
    }

    @Override
    public void update(long delta) {
        setPreviousData();
        if (speed != 0) {
            double nextX = getGameObject().getX() + speed * Math.cos(Math.toRadians(getDirection())) * ((double) delta / 1000000000);
            double nextY = getGameObject().getY() - speed * Math.sin(Math.toRadians(getDirection())) * ((double) delta / 1000000000);
            getGameObject().setPosition(nextX, nextY);
        }
    }

    private void setPreviousData() {
        xPrevious = getGameObject().getX();
        yPrevious = getGameObject().getY();
        directionPrevious = getGameObject().getDirection();
    }
}
