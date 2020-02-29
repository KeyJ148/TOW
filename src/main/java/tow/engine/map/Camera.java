package tow.engine.map;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Position;

public class Camera {

    private GameObject followObject;//Объект, за которым следует камера (Если followObject != null, то x и y не учитываются)
    private double x = 0, y = 0;//Абсолютная позиция камеры в комнате, отрисовка происходит вокруг этой позиции

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setFollowObject(GameObject gameObject){
        if (!gameObject.hasComponent(Position.class)) return;
        followObject = gameObject;
    }

    public GameObject getFollowObject(){
        return followObject;
    }

    public void deleteFollowObject(){
        followObject = null;
    }

    //Расчёт текущей позиции
    public void update(){
        if (followObject != null){
            x = followObject.getComponent(Position.class).x;
            y = followObject.getComponent(Position.class).y;
        }

        int width = Global.engine.render.getWidth();
        int height = Global.engine.render.getHeight();
        int widthMap = Global.location.width;
        int heightMap = Global.location.height;

        if (x < width/2) x = width/2;
        if (y < height/2) y = height/2;

        if (x > widthMap-width/2) x = widthMap-width/2;
        if (y > heightMap-height/2) y = heightMap-height/2;

        if (Global.engine.render.getWidth() > widthMap) x = widthMap/2;
        if (Global.engine.render.getHeight() > heightMap) y = heightMap/2;
    }

    //Преобразует координаты относительно угла карты в координаты относительно угла экрана (области видимости камеры)
    public Vector2<Integer> toRelativePosition(Vector2 absolutePosition){
        //Преобразуем входной верктор в int
        if (absolutePosition.x.getClass().equals(Double.class)) absolutePosition.x = ((Double) absolutePosition.x).intValue();
        if (absolutePosition.y.getClass().equals(Double.class)) absolutePosition.y = ((Double) absolutePosition.y).intValue();

        Vector2<Integer> relativePosition = new Vector2();
        relativePosition.x = (int) (Global.engine.render.getWidth()/2 - (x - (int) absolutePosition.x));
        relativePosition.y = (int) (Global.engine.render.getHeight()/2 - (y - (int) absolutePosition.y));

        return relativePosition;
    }
}
