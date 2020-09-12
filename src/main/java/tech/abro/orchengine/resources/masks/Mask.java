package tech.abro.orchengine.resources.masks;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.Vector2;
import tech.abro.orchengine.logger.Logger;
import tech.abro.orchengine.resources.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mask {

    private int width, height;
    private Vector2<Integer>[] maskCenter;//Позиции точек в полигоне маски (относительно центра)
    private Vector2<Integer>[] maskDefault;//Позиции точек в полигоне маски (относительно верхнего левого угла)

    public Mask(List<Vector2<Integer>> mask){
        Vector2<Integer>[] maskArray = mask.toArray(new Vector2[0]);
        for (Vector2<Integer> maskPoint : maskArray){
            width = Math.max(width, maskPoint.x);
            height = Math.max(height, maskPoint.y);
        }
        //TODO: переделать все на List
        //TODO: избавиться от высоты и ширины

        this.maskDefault = maskArray;
        this.maskCenter = center(maskArray, width, height);
    }

    //Загрузка маски из файла
    public Vector2<Integer>[] loadFromResources(String path, int width, int height){
        try (BufferedReader reader = ResourceLoader.getResourceAsBufferedReader(path)){


            ArrayList<Vector2<Integer>> maskArr = new ArrayList<>();
            String s;

            while ((s = reader.readLine()) != null) {
                int x = Integer.parseInt(s.substring(0, s.indexOf(' ')));
                int y = Integer.parseInt(s.substring(s.indexOf(' ') + 1));
                maskArr.add(new Vector2(x, y));
            }

            Global.logger.println("Load mask \"" + path + "\" completed", Logger.Type.DEBUG_MASK);
            Vector2<Integer>[] result = new Vector2[maskArr.size()];

            return maskArr.toArray(result);
        } catch (IOException e) {
            Global.logger.println("Load mask \"" + path +"\" error", Logger.Type.ERROR);
        }

        return createDefault(width, height);
    }

    //Создание маски по границам исходной картинки (относительно левого верхнего угла)
    public Vector2<Integer>[] createDefault(int width, int height){
        Vector2<Integer>[] mask = new Vector2[4];

        mask[0] = new Vector2(0, 0);
        mask[1] = new Vector2(width-1, 0);
        mask[2] = new Vector2(width-1, height-1);
        mask[3] = new Vector2(0, height-1);

        return mask;
    }

    //Установка координат маски относительно центра объекта (изначально относительно левого верхнего угла)
    public Vector2<Integer>[] center(Vector2<Integer>[] mask, int width, int height){
        Vector2<Integer>[] maskDraw = new Vector2[mask.length];

        for (int i=0; i<mask.length; i++){
            maskDraw[i] = new Vector2();
            maskDraw[i].x = mask[i].x - width/2;
            maskDraw[i].y = mask[i].y - height/2;
        }

        return maskDraw;
    }

    public Vector2<Integer>[] getMaskCenter(){
        Vector2<Integer>[] copy = new Vector2[maskCenter.length];
        for (int i=0; i<maskCenter.length; i++)
            copy[i] = new Vector2(maskCenter[i]);

        return copy;
    }

    public Vector2<Integer>[] getMaskDefault(){
        Vector2<Integer>[] copy = new Vector2[maskDefault.length];
        for (int i=0; i<maskDefault.length; i++)
            copy[i] = new Vector2(maskDefault[i]);

        return copy;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
