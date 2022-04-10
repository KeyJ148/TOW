package cc.abro.tow.client.settings;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.JsonContainerLoader;
import cc.abro.tow.client.ClientData;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@GameService
public class SettingsService {

    private static final String PATH = "setting.json";

    private boolean loadSuccess;
    private Settings settings;

    public SettingsService() {
        try {
            loadSettingsFromDisk();
            loadSuccess = true;
        } catch (IOException e) {
            log.warn("Settings can't load. Create default settings and save it to disk.");
            log.debug(e);
            loadSuccess = false;
            setDefaultSettings();
            saveSettingsToDiskAsync();
        }
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

        settings.profile.nickname = nickname;
        settings.profile.color = tankColor.getRGBArray();
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
}
