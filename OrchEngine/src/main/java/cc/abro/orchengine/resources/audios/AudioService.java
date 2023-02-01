package cc.abro.orchengine.resources.audios;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.location.Camera;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.picocontainer.Startable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.lwjgl.openal.ALC10.*;

@EngineService
public class AudioService implements Startable {

    private final List<AudioSource> audioSources = new LinkedList<>();
    private long context, device;
    @Getter
    @Setter
    private double volume = 100;

    @Override
    public void start() {
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        device = alcOpenDevice(defaultDeviceName);
        int[] attributes = {0};
        context = alcCreateContext(device, attributes);
        alcMakeContextCurrent(context);
        AL.createCapabilities(ALC.createCapabilities(device));
    }

    @Override
    public void stop() {
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

    public void playSoundEffect(Audio audio, int x, int y, int range) {
        /*
        Т.к. при приближении к границе экрана абсолютная позиция камеры фиксируется,
        вне зависимости от положения объекта за которым следует камера, то получается,
        что при приближении к границе экрана звук становится слышен тише, хотя источник
        звука находится на таком же расстояние от объекта игрока, как и раньше
        Условие (camera.isSoundOnFollowingObject()) исправляет этот баг
        */
        Camera camera = Context.getService(LocationManager.class).getActiveLocation().getCamera();
        Optional<GameObject> cameraFollowObject = camera.isSoundOnFollowingObject() ?
                camera.getFollowObject() : Optional.empty();
        double listenerX = cameraFollowObject
                .map(follow -> follow.getComponent(Position.class).x)
                .orElse(camera.getX());
        double listenerY = cameraFollowObject
                .map(follow -> follow.getComponent(Position.class).y)
                .orElse(camera.getY());

        double dis = Math.sqrt(Math.pow(x - listenerX, 2) + Math.pow(y - listenerY, 2));
        if (dis < range) {
            float soundVolume = (float) (volume * (1 - (dis / range)));
            playSoundEffect(audio, soundVolume);
        }
    }

    //Проигрывание с громкостью в диапазоне [0; 1]
    private void playSoundEffect(Audio audio, float volume) {
        deleteWasteAudioSources();

        AudioSource source = new AudioSource();
        source.setAudio(audio);
        source.setVolume(volume);
        source.play();
        audioSources.add(source);
    }

    //Удаление из списка и из памяти источников, которые закончили проигрывание звука
    private void deleteWasteAudioSources() {
        Iterator<AudioSource> it = audioSources.listIterator();
        while (it.hasNext()) {
            AudioSource source = it.next();

            if (source.isStopped()) {
                source.delete();
                it.remove();
            }
        }
    }
}
