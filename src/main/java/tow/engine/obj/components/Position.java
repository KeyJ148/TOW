package tow.engine.obj.components;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.map.Room;
import tow.engine.obj.Obj;

public class Position extends Component {

    public double x;
    public double y;
    public int depth;
    private double directionDraw; //0, 360 - в право, против часовой - отрисовка
    public boolean absolute = true; //Позиция относительно угла карты? (Иначе относительно угла экрана)

    public Room room;//Комната в которой находится объект
    public int id;//Уникальный номер объекта в комнате

    public Position(Obj obj, double x, double y, int depth){
        this(obj, x, y, depth, 90);
    }

    public Position(Obj obj, double x, double y, int depth, double directionDraw) {
        super(obj);
        this.x = x;
        this.y = y;
        this.depth = depth;
        setDirectionDraw(directionDraw);
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
            return Global.room.camera.toRelativePosition(new Vector2(x, y));
        }
    }

}
