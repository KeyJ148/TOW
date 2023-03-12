package cc.abro.orchengine.resources.masks;

import cc.abro.orchengine.util.Vector2;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Mask {

    private final List<Vector2<Integer>> maskPoints; //Позиции точек в полигоне маски (относительно центра)

    public Mask(List<Vector2<Integer>> mask) {
        maskPoints = new ArrayList<>(mask);
    }

    public List<Vector2<Integer>> getPoints() {
        return new ArrayList<>(maskPoints);
    }

    public int getCountPoints() {
        return maskPoints.size();
    }
}
