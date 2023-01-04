package cc.abro.orchengine.resources.masks;

import cc.abro.orchengine.resources.JsonContainerLoader;
import cc.abro.orchengine.util.Vector2;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class MaskLoader {

    public static Mask getMask(String path, int width, int height) {
        try {
            Mask mask = loadMaskPointsFromFile(path, width, height);

            log.debug("Load mask \"" + path + "\" completed");
            return mask;
        } catch (Exception e) {
            log.error("Mask \"" + path + "\" not loading");
            return null;
        }
    }

    //Создание маски по границам исходной картинки (относительно левого верхнего угла)
    public static Mask createDefaultMask(int width, int height) {
        List<Vector2<Integer>> maskPoints = new ArrayList<>();
        maskPoints.add(new Vector2<>(0, 0));
        maskPoints.add(new Vector2<>(width - 1, 0));
        maskPoints.add(new Vector2<>(width - 1, height - 1));
        maskPoints.add(new Vector2<>(0, height - 1));

        return new Mask(maskPoints, width, height);
    }

    //Загрузка маски из файла
    private static Mask loadMaskPointsFromFile(String path, int width, int height) throws IOException {
        MaskPoint[] maskPoints = JsonContainerLoader.loadInternalFile(MaskPoint[].class, path);

        List<Vector2<Integer>> maskPointsList = new ArrayList<>();
        for (MaskPoint maskPoint : maskPoints) {
            maskPointsList.add(new Vector2<>(maskPoint.x, maskPoint.y));
        }

        return new Mask(maskPointsList, width, height);
    }

    private static record MaskPoint(int x, int y) {
    }
}
