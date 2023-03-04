package cc.abro.orchengine.resources.masks;

import cc.abro.orchengine.resources.JsonContainerLoader;
import cc.abro.orchengine.util.Vector2;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@UtilityClass
public class MaskLoader {

    public Mask getMaskFromFile(String path, int width, int height) {
        try {
            //Загружаем маску относительно левого верхнего угла
            List<Vector2<Integer>> maskPoints = loadMaskPointsFromFile(path);
            //Преобразуем маску относительно центра
            List<Vector2<Integer>> centeringMaskPoints = centering(maskPoints, width, height);

            log.debug("Load mask \"" + path + "\" completed");
            return new Mask(centeringMaskPoints);
        } catch (Exception e) {
            log.error("Mask \"" + path + "\" not loading");
            return null;
        }
    }

    //Создание маски по границам исходной картинки
    public Mask getRectangularMask(int width, int height) {
        //Создаем маску относительно левого верхнего угла
        List<Vector2<Integer>> maskPoints = new ArrayList<>();
        maskPoints.add(new Vector2<>(0, 0));
        maskPoints.add(new Vector2<>(width - 1, 0));
        maskPoints.add(new Vector2<>(width - 1, height - 1));
        maskPoints.add(new Vector2<>(0, height - 1));

        //Преобразуем маску относительно центра
        List<Vector2<Integer>> centeringMaskPoints = centering(maskPoints, width, height);

        return new Mask(centeringMaskPoints);
    }

    //Установка координат маски относительно центра объекта (изначально относительно левого верхнего угла)
    private List<Vector2<Integer>> centering(List<Vector2<Integer>> maskPoints, int width, int height) {
        List<Vector2<Integer>> centeringMaskPoints = new ArrayList<>(maskPoints.size());

        for (Vector2<Integer> maskPoint : maskPoints) {
            centeringMaskPoints.add(new Vector2<>(
                    maskPoint.x - width / 2,
                    maskPoint.y - height / 2
            ));
        }
        return centeringMaskPoints;
    }

    //Загрузка маски из файла
    private List<Vector2<Integer>> loadMaskPointsFromFile(String path) throws IOException {
        MaskPoint[] maskPointsArray = JsonContainerLoader.loadInternalFile(MaskPoint[].class, path);

        List<Vector2<Integer>> maskPoints = new ArrayList<>();
        for (MaskPoint maskPoint : maskPointsArray) {
            maskPoints.add(new Vector2<>(maskPoint.x, maskPoint.y));
        }

        return maskPoints;
    }

    private record MaskPoint(int x, int y) {
    }
}
