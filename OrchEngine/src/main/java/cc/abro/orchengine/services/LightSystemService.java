package cc.abro.orchengine.services;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.util.Vector2;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EngineService
public class LightSystemService {
    public static boolean[][] frameMask; //true, если занято
    public static int[][] light; //от 0 до 255 затемнение (255 - полная темнота)

    public static final int DARK = 3;

    public static void init(int w, int h) {
        frameMask = new boolean[w][h];
        light = new int[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                light[i][j] = DARK;
            }
        }
    }

    public static void render(int x, int y, Collision collision) {
        if (collision.getGameObject().getClass().getName().contains("Player")) return;

        Polygon p = new Polygon();

        List<Vector2<Double>> maskDrawPoints = collision.getAbsoluteMaskPoints().stream()
                .map(collision.getLocationManager().getActiveLocation().getCamera()::toRelativePosition)
                .peek(v2 -> p.addPoint(v2.x.intValue(), v2.y.intValue()))
                .toList();
        Rectangle2D rec = p.getBounds2D();

        for (int i = (int) rec.getMinX(); i < rec.getMaxX(); i++) {
            for (int j = (int) rec.getMinY(); j < rec.getMaxY(); j++) {
                if (i > 0 && j > 0 && i < frameMask.length && j < frameMask[0].length) {
                    frameMask[i][j] = true;
                }
            }
        }

        GL11.glLoadIdentity();
        Color.BLUE.bind();
        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (Vector2<Double> maskPoint : maskDrawPoints) {
            GL11.glVertex2f(maskPoint.x.floatValue(), maskPoint.y.floatValue());
        }
        GL11.glEnd();
    }

    public static final int COUNT_DIR = 2500;

    public static void light(int x, int y, int range, int lightLevel) {
        Vector2<Double> v2 = Context.getService(LocationManager.class).getActiveLocation().getCamera().toRelativePosition(new Vector2<Double>((double) x, (double) y));
        x = v2.x.intValue();
        y = v2.y.intValue();

        for (int i = 0; i < COUNT_DIR; i++) {
            double dir = Math.toRadians(360.0 / COUNT_DIR * i) - Math.PI / 2;
            double cos = Math.cos(dir);
            double sin = Math.sin(dir);

            for (int dis = 0; dis < range; dis++) {
                int checkX = x + (int) (cos * dis);
                int checkY = y - (int) (sin * dis);
                if (checkX > 0 && checkY > 0 && checkX < frameMask.length && checkY < frameMask[0].length) {
                    if (frameMask[checkX][checkY]) break;
                    int delta = DARK - lightLevel;
                    int range_dark = range / 10;
                    light[checkX][checkY] = lightLevel + ((dis > range - range_dark) ? delta / range_dark * (range_dark - (range - dis)) : 0);
                }
            }
        }
    }

    public static void light_dir(int x, int y, int drawDir, int range, int lightLevel) {
        Vector2<Double> v2 = Context.getService(LocationManager.class).getActiveLocation().getCamera().toRelativePosition(new Vector2<Double>((double) x, (double) y));
        x = v2.x.intValue();
        y = v2.y.intValue();

        for (int i = 0; i < COUNT_DIR; i++) {
            double dir = 80.0 / COUNT_DIR * i;
            dir = Math.toRadians(drawDir - 40 + dir);
            double cos = Math.cos(dir);
            double sin = Math.sin(dir);

            for (int dis = 0; dis < range; dis++) {
                int checkX = x + (int) (cos * dis);
                int checkY = y - (int) (sin * dis);
                if (checkX > 0 && checkY > 0 && checkX < frameMask.length && checkY < frameMask[0].length) {
                    if (frameMask[checkX][checkY]) break;
                    int delta = DARK - lightLevel;
                    int range_dark = range / 10;
                    light[checkX][checkY] = lightLevel + ((dis > range - range_dark) ? delta / range_dark * (range_dark - (range - dis)) : 0);
                }
            }
        }
    }

    public static void render() {
        Set<Integer> lightLevels = new HashSet<>();
        for (int i = 0; i < light.length; i++) {
            for (int j = 0; j < light[i].length; j++) {
                lightLevels.add(light[i][j]);
            }
        }

        GL11.glLoadIdentity();
        for (Integer lightLevel : lightLevels) {
            new Color(0, 0, 0, lightLevel).bind();
            if (lightLevel < 2) new Color(200, 200, 0, 100).bind(); //Убрать, если не нужен желтый свет
            GL11.glBegin(GL11.GL_POINTS);
            for (int i = 0; i < light.length; i++) {
                for (int j = 0; j < light[i].length; j++) {
                    if (light[i][j] == lightLevel) GL11.glVertex2f(i, j);
                }
            }
            GL11.glEnd();
        }
    }

}
