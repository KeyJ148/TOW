package tech.abro.orchengine.gameobject.components;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.Vector2;
import tech.abro.orchengine.gameobject.QueueComponent;
import tech.abro.orchengine.map.Location;

import java.util.Arrays;
import java.util.List;

public class Position extends QueueComponent {

    public double x;
    public double y;
    public int depth;
    private double directionDraw; //0, 360 - в право, против часовой - отрисовка
    public boolean absolute = true; //Позиция относительно угла карты? (Иначе относительно угла экрана)

    public Location location;//Комната в которой находится объект
    public int id;//Уникальный номер объекта в комнате

    public Position(double x, double y, int depth){
        this(x, y, depth, 90);
    }

    public Position(double x, double y, int depth, double directionDraw) {
        this.x = x;
        this.y = y;
        //TODO: поменять -depth на depth после замены в position и далее depth на z
        this.depth = -depth;
        setDirectionDraw(directionDraw);
    }

    @Override
    public void updateComponent(long delta){ }

    @Override
    protected void drawComponent() { }

    @Override
    public void destroy() {
        location.objDel(id);
    }

    public double getDirectionDraw(){
        if (directionDraw%360 >= 0){
            return directionDraw%360;
        } else {
            return 360-Math.abs(directionDraw%360);
        }
    }

    public void setDirectionDraw(double directionDraw){
        this.directionDraw = directionDraw;
    }

    public Vector2<Integer> getRelativePosition(){
        if (!absolute){
            Vector2<Integer> relativePosition = new Vector2<>();
            relativePosition.x = (int) x;
            relativePosition.y = (int) y;
            return relativePosition;
        } else {
            return Global.location.camera.toRelativePosition(new Vector2<>(x, y));
        }
    }

    @Override
    public Class getComponentClass() {
        return Position.class;
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
