package tow.engine3.image;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine3.obj.Obj;

public class Camera {

    private static Obj camera;//Объект, за которым следует камера

    public static double absoluteX;//Абсолютная позиция камеры на карте
    public static double absoluteY;//Отрисовка происходит вокруг этой позиции

    public static void setFollowObject(Obj obj){
        if (obj.position == null) return;
        camera = obj;
    }

    public static Obj getFollowObject(){
        return camera;
    }

    public static void deleteFollowObject(){
        camera = null;
    }

    public static void calc(){
        if (camera == null){
            absoluteX = 0;
            absoluteY = 0;
        } else {
            absoluteX = camera.position.x;
            absoluteY = camera.position.y;
        }

        int width = Global.engine.render.getWidth();
        int height = Global.engine.render.getHeight();
        int widthMap = Global.room.width;
        int heightMap = Global.room.height;

        if (absoluteX < width/2){
            absoluteX = width/2;
        }
        if (absoluteY < height/2){
            absoluteY = height/2;
        }
        if (absoluteX > widthMap-width/2){
            absoluteX = widthMap-width/2;
        }
        if (absoluteY >heightMap-height/2){
            absoluteY = heightMap-height/2;
        }

        if (Global.engine.render.getWidth() > widthMap){
            absoluteX = widthMap/2;
        }
        if (Global.engine.render.getHeight() > heightMap){
            absoluteY = heightMap/2;
        }
    }

    //Преобразует координаты относительно угла карты в координаты относительно угла экрана (области видимости камеры)
    public static Vector2<Integer> toRelativePosition(Vector2 absolutePosition){
        //Преобразуем входной верктор в int
        if (absolutePosition.x.getClass().equals(Double.class)) absolutePosition.x = ((Double) absolutePosition.x).intValue();
        if (absolutePosition.y.getClass().equals(Double.class)) absolutePosition.y = ((Double) absolutePosition.y).intValue();

        Vector2<Integer> relativePosition = new Vector2();
        relativePosition.x = (int) (Global.engine.render.getWidth()/2 - (absoluteX - (int) absolutePosition.x));
        relativePosition.y = (int) (Global.engine.render.getHeight()/2 - (absoluteY - (int) absolutePosition.y));

        return relativePosition;
    }
}
