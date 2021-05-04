package tow.engine.resources.masks;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.logger.Logger;
import tow.engine.resources.JsonContainerLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaskLoader {

    public static Mask getMask(String path){
        try{
            Mask mask = new Mask(loadMaskPointsFromFile(path));

            Global.logger.println("Load mask \"" + path + "\" completed", Logger.Type.DEBUG_MASK);
            return mask;
        } catch (Exception e){
            Global.logger.println("Mask \"" + path + "\" not loading", Logger.Type.ERROR);
            return null;
        }
    }

    public static Mask createDefaultMask(int width, int height){
        List<Vector2<Integer>> maskPoints = new ArrayList<>();
        maskPoints.add(new Vector2<>(0, 0));
        maskPoints.add(new Vector2<>(width-1, 0));
        maskPoints.add(new Vector2<>(width-1, height-1));
        maskPoints.add(new Vector2<>(0, height-1));

        return new Mask(maskPoints);
    }

    private static List<Vector2<Integer>> loadMaskPointsFromFile(String path) throws IOException {
        MaskPoint[] maskPoints = JsonContainerLoader.loadInternalFile(MaskPoint[].class, path);

        List<Vector2<Integer>> maskPointsList = new ArrayList<>();
        for(MaskPoint maskPoint : maskPoints){
            maskPointsList.add(new Vector2<>(maskPoint.x, maskPoint.y));
        }

        return maskPointsList;
    }

    private static class MaskPoint{
        int x, y;
    }
}
