package cc.abro.tow.client.settings;

import java.io.IOException;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.JsonContainerLoader;
import cc.abro.tow.client.ClientData;
import lombok.extern.log4j.Log4j2;

@Log4j2
@GameService
public class SettingsService {

    private static final String PATH = "setting.json";

    private final boolean loadSuccess;
    private Settings settings;

    public SettingsService() {
        boolean loadSuccess = true;
        try {
            loadSettingsFromDisk();
        } catch (IOException e) {
            loadSuccess = false;
            log.warn("Settings can't load. Create default settings.");
            log.debug(e);
            setDefaultSettings();
        }
        this.loadSuccess = loadSuccess;
    }

    public void setDefaultSettings() {
        settings = new Settings();
    }

    public void loadSettingsFromDisk() throws IOException {
        settings = JsonContainerLoader.loadExternalFile(Settings.class, PATH);
    }

    public void saveSettingsToDisk() throws IOException {
        JsonContainerLoader.saveExternalFile(settings, PATH);
    }

    public void saveSettingsToDiskAsync() {
        new Thread(() -> {
            try {
                saveSettingsToDisk();
            } catch (IOException e) {
                log.error("Settings can't be saved", e);
            }
        });
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public boolean isLoadSuccess() {
        return loadSuccess;
    }

    public void setProfileSettings(String nickname, Color tankColor){
        if (nickname.isEmpty()) {
            throw new EmptyNicknameException();
        }

        settings.getProfile().setNickname(nickname);
        settings.getProfile().setColor(tankColor.getRGBArray());
        try {
            saveSettingsToDisk();
        } catch (IOException e) {
            log.warn("Settings can't be saved", e);
            throw new CantSaveSettingException();
        }

        Context.getService(ClientData.class).name = nickname;
        Context.getService(ClientData.class).color = tankColor;
    }

    public class EmptyNicknameException extends RuntimeException {}
    public class CantSaveSettingException extends RuntimeException {}

    public void setVolumeSettings(double musicVolume, double soundVolume) {

        settings.getVolume().setMusicVolume(musicVolume);
        settings.getVolume().setSoundVolume(soundVolume);
        try {
            saveSettingsToDisk();
        } catch (IOException e) {
            log.warn("Settings can't be saved", e);
            throw new CantSaveSettingException();
        }

        Context.getService(ClientData.class).musicVolume = musicVolume;
        Context.getService(ClientData.class).soundVolume = soundVolume;
    }
}
