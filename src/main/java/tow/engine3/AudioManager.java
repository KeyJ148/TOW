package tow.engine3;

import tow.engine3.image.Camera;
import tow.engine.Global;

import java.io.File;

//TODO
public class AudioManager {

    public static final String PATH_TO_AUDIO_ROOT = "res/audio/";

    //private static TreeMap<String, Audio> audios = new TreeMap<>();

    public static void init(){
        String[] audios = Global.storage.getAudios();

        //loadFromDirectory(Global.getFile(PATH_TO_AUDIO_ROOT), "wav");
        for(int i=0; i<audios.length; i++){
            //load(Global.getFile(audios[i]));
        }
    }

    /*
    private static void loadFromDirectory(File directiory, String format){
        for(File file : directiory.listFiles()){
            if (file.isDirectory()){
                loadFromDirectory(file, format);
            } else {
                String name = file.getName();
                //if (name.substring(name.lastIndexOf(".")+1).equals(format)) load(file);
            }
        }
    }
*/
    /*
    private static void load(File file){
        try{
            Audio audio = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(file.getPath()));

            String name = file.getName();
            String shortName = name.substring(0, name.lastIndexOf("."));
            audios.put(shortName, audio);

            Logger.println("Load audio \"" + file.getPath() + "\" complited", Logger.Type.DEBUG_AUDIO);
        } catch (Exception e1) {
            Logger.println("Audio \"" + file.getPath() + "\" not loading", Logger.Type.ERROR);
        }
    }

    public static Audio getAudio(String name){
        if (audios.containsKey(name)) return audios.get(name);

        Logger.println("Not find audio: " + name, Logger.Type.ERROR);
        return null;
    }

    public static void playSoundEffect(String name){
        Audio audio = getAudio(name);
        if (audio != null) audio.playAsSoundEffect(1.0f, (float) SettingsStorage.MUSIC.SOUND_VOLUME, false);
    }
    */


    public static void playSoundEffect(String name, int x, int y, int range){
        //TODO Audio audio = getAudio(name);

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

        /*
        if (audio != null && dis < range){
            float soundVolume = (float) (SettingsStorage.MUSIC.SOUND_VOLUME * (1-(dis/range)));
            audio.playAsSoundEffect(1.0f, soundVolume, false);
        }*/
    }

}
