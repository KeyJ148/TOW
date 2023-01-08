package cc.abro.orchengine.resources.masks;

import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class Mask {

    @Getter
    private final int width, height;
    private Vector2<Integer>[] maskCenter;//Позиции точек в полигоне маски (относительно центра)
    private Vector2<Integer>[] maskDefault;//Позиции точек в полигоне маски (относительно верхнего левого угла)

    public Mask(List<Vector2<Integer>> mask, int width, int height) {
        this.width = width;
        this.height = height;

        Vector2<Integer>[] maskArray = mask.toArray(new Vector2[0]);
        this.maskDefault = maskArray;
        this.maskCenter = center(maskArray, width, height);
    }

    //Установка координат маски относительно центра объекта (изначально относительно левого верхнего угла)
    public Vector2<Integer>[] center(Vector2<Integer>[] mask, int width, int height) {
        Vector2<Integer>[] maskDraw = new Vector2[mask.length];

        for (int i = 0; i < mask.length; i++) {
            maskDraw[i] = new Vector2();
            maskDraw[i].x = mask[i].x - width / 2;
            maskDraw[i].y = mask[i].y - height / 2;
        }

        return maskDraw;
    }

    public Vector2<Integer>[] getMaskCenter() {
        Vector2<Integer>[] copy = new Vector2[maskCenter.length];
        for (int i = 0; i < maskCenter.length; i++)
            copy[i] = new Vector2(maskCenter[i]);

        return copy;
    }

    public Vector2<Integer>[] getMaskDefault() {
        Vector2<Integer>[] copy = new Vector2[maskDefault.length];
        for (int i = 0; i < maskDefault.length; i++)
            copy[i] = new Vector2(maskDefault[i]);

        return copy;
    }
}
