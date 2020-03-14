package tow.game;

import org.lwjgl.system.CallbackI;
import tow.engine.Loader;
import tow.engine.Vector2;
import tow.engine.resources.JsonContainerLoader;
import tow.game.client.Game;
import tow.game.client.NetGameRead;
import tow.game.client.Storage;
import tow.game.server.NetServerRead;
import tow.game.server.Server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GameStart {

    public static class MaskPoint{
        int x, y;
    }

    public static void main(String[] args) throws IOException {
        Loader.start(new Game(), new NetGameRead(), new Storage(), new Server(), new NetServerRead());

        /* Ген и считывание масок
        Vector2<Integer>[] vector2Array = new Vector2[4];
        for (int i = 0; i < vector2Array.length; i++) {
            vector2Array[i] = new Vector2<>(i, i*i);
        }

        JsonContainerLoader.saveExternalFile(vector2Array, "mask.json");
        MaskPoint[] vectorLoad = JsonContainerLoader.loadExternalFile(MaskPoint[].class, "mask.json");
        System.out.println(vectorLoad[0].x);
        System.out.println(vectorLoad[1].x);
        System.out.println(vectorLoad[2].x);
        System.out.println(vectorLoad[3].x);
        */

        /* Ген конфигов текстур и анимаций
        List<Sprite> listS = new ArrayList<>();
        String[][] strS = new Storage().getImages();
        for (int i = 0; i < strS.length; i++) {
            Sprite s = new Sprite();
            s.name = strS[i][0].substring(strS[i][0].lastIndexOf("/")+1, strS[i][0].length()-4);
            s.texturePath = "graphic/sprites/textures/" + strS[i][0];
            s.maskPath = "graphic/sprites/masks/" + strS[i][0].substring(0, strS[i][0].length()-4) + ".json";

            listS.add(s);
        }
        JsonContainerLoader.saveExternalFile(listS, "spr.json");


        List<Anim> listA = new ArrayList<>();
        String[][] strA = new Storage().getAnimations();
        for (int i = 0; i < strA.length; i++) {
            Anim s = new Anim();
            s.name = strA[i][0].substring(strA[i][0].lastIndexOf("/")+1);
            s.texturesPackPaths = new String[]{"graphic/sprite_packs/textures_packs/" + strA[i][0], "2"};
            s.maskPath = "graphic/sprite_packs/masks/" + strA[i][0] + ".json";

            listA.add(s);
        }
        JsonContainerLoader.saveExternalFile(listA, "anim.json");*/

        //normalizeFile(new File("res/graphics"));
    }

    public static void normalizeFile(File f){
        if (!f.exists()) return;
        if (f.isDirectory()){
            File[] files = f.listFiles();
            for(File file : files) normalizeFile(file);
            return;
        }
        String name = f.getName();
        if (!name.substring(name.length()-4, name.length()).equals(".txt")) return;

        System.out.println("Normalize: " + name);

        String path = f.getAbsolutePath();
        File newFile = new File(path.substring(0, path.length()-4) + ".json");
        String newFilePath = newFile.getPath();
        try {
            List<MaskPoint> maskPoints = new ArrayList<>();
            Scanner scanner = new Scanner(f);
            while(scanner.hasNextInt()){
                MaskPoint maskPoint = new MaskPoint();
                maskPoint.x = scanner.nextInt();
                maskPoint.y = scanner.nextInt();
                maskPoints.add(maskPoint);
            }

            MaskPoint[] maskPointsArray = maskPoints.toArray(new MaskPoint[0]);
            String savePath = newFilePath.substring(newFilePath.indexOf("res"));
            System.out.println(savePath);
            JsonContainerLoader.saveExternalFile(maskPointsArray, savePath);

            f.delete();
        } catch (IOException e){ e.printStackTrace(); }
    }

    public static class Sprite {
        String name;
        String texturePath = "graphic/sprites/images/aasdas.png";
        String maskPath = "graphic/sprites/images/mask.json";
    }

    public static class Anim {
        String name;
        String[] texturesPackPaths = {"graphic/sprites/images/aasdas.png", "2", "3"};
        String maskPath = "graphic/sprites/images/mask.json";
    }
}
