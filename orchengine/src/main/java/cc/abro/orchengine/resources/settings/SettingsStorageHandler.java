package cc.abro.orchengine.resources.settings;

import java.io.IOException;

public class SettingsStorageHandler {

    public static void init() throws IOException {
        initExternalSettings();
        initInternalSettings();
    }

    public static void setDefaultSettings() throws IOException {
        saveDefaultSettingsToExternalSettings();
        initExternalSettings();
    }

    private static void initExternalSettings() throws IOException {
        SettingsStorage.DISPLAY = initExternalSettingsOrDefaultFromInternal(SettingsStorage.Display.class);
        SettingsStorage.LOGGER = initExternalSettingsOrDefaultFromInternal(SettingsStorage.Logger.class);
        SettingsStorage.MUSIC = initExternalSettingsOrDefaultFromInternal(SettingsStorage.Music.class);
    }

    private static <T> T initExternalSettingsOrDefaultFromInternal(Class<T> settingsContainerClass) throws IOException {
        try {
            return SettingsLoader.loadExternalSettings(settingsContainerClass);
        } catch (Exception e) {
            saveDefaultSettingsToExternalSettings(settingsContainerClass);
            return SettingsLoader.loadExternalSettings(settingsContainerClass);
        }
    }

    private static void initInternalSettings() throws IOException {
        SettingsStorage.NETWORK = SettingsLoader.loadInternalSettings(SettingsStorage.Network.class);
    }

    private static void saveDefaultSettingsToExternalSettings() throws IOException {
        saveDefaultSettingsToExternalSettings(SettingsStorage.Display.class);
        saveDefaultSettingsToExternalSettings(SettingsStorage.Music.class);
        saveDefaultSettingsToExternalSettings(SettingsStorage.Logger.class);
    }

    private static <T> void saveDefaultSettingsToExternalSettings(Class<T> settingsContainerClass) throws IOException {
        T settingContainerObject = SettingsLoader.loadInternalSettings(settingsContainerClass);
        SettingsLoader.saveExternalSettings(settingContainerObject);
    }
}
