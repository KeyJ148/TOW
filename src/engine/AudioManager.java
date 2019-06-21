package engine;

import engine.image.Camera;
import engine.io.Logger;
import engine.setting.SettingStorage;
import game.client.Storage;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public class AudioManager {

    public static final String PATH_TO_AUDIO_ROOT = "res/audio/";

    private static TreeMap<String, Audio> audios = new TreeMap<>();

    public static void init(){
        loadFromDirectory(new File(PATH_TO_AUDIO_ROOT), "wav");
        for(int i=0; i<Storage.audio.length; i++){
            load(Storage.audio[i]);
        }
    }

    private static void loadFromDirectory(File directiory, String format){
        for(File file : directiory.listFiles()){
            if (file.isDirectory()){
                loadFromDirectory(file, format);
            } else {
                String name = file.getName();
                if (name.substring(name.lastIndexOf(".")+1).equals(format)) load(file.getPath());
            }
        }
    }

    private static void load(String path){
        try{
            Audio audio = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(path));

            String name = new File(path).getName();
            String shortName = name.substring(0, name.lastIndexOf("."));
            audios.put(shortName, audio);

            Logger.println("Load audio \"" + path + "\" complited", Logger.Type.DEBUG_AUDIO);
        } catch (IOException e1) {
            Logger.println("Audio \"" + path + "\" not loading", Logger.Type.ERROR);
        }
    }

    public static Audio getAudio(String name){
        if (audios.containsKey(name)) return audios.get(name);

        Logger.println("Not find audio: " + name, Logger.Type.ERROR);
        return null;
    }

    public static void playSoundEffect(String name){
        Audio audio = getAudio(name);
        if (audio != null) audio.playAsSoundEffect(1.0f, (float) SettingStorage.Music.SOUND_VOLUME, false);
    }

    public static void playSoundEffect(String name, int x, int y, int range){
        Audio audio = getAudio(name);

        double cameraX = Camera.absoluteX;
        double cameraY = Camera.absoluteY;

        /*
        Т.к. при приближение к границе экрана абсолютная позиция камеры фиксирцуется,
        вне зависимости от положения объекта за которым следует камера, то получается,
        что при приближение к границы экрана звук становится слышен тише, хотя источник
        звука находится на таком же расстояние от объекта камеры как и раньше
        Это условие исправляет этот баг
        */
        if (Camera.getFollowObject() != null){
            cameraX = Camera.getFollowObject().position.x;
            cameraY = Camera.getFollowObject().position.y;
        }

        double dis = Math.sqrt(Math.pow(x-cameraX, 2) + Math.pow(y-cameraY, 2));

        if (audio != null && dis < range){
            float soundVolume = (float) (SettingStorage.Music.SOUND_VOLUME * (1-(dis/range)));
            audio.playAsSoundEffect(1.0f, soundVolume, false);
        }
    }
}
