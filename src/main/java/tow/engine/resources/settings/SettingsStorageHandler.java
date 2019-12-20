package tow.engine.resources.settings;

import java.io.IOException;

public class SettingsStorageHandler {

    public static void init() throws IOException{
        initExternalSettings();
        initInternalSettings();
    }

    public static void setDefaultSettings() throws IOException{
        saveDefaultSettingsToExternalSettings();
        initExternalSettings();
    }

    private static void initExternalSettings() throws IOException {
        SettingsStorage.DISPLAY = SettingsLoader.loadExternalSettings(SettingsStorage.Display.class);
        SettingsStorage.MUSIC = SettingsLoader.loadExternalSettings(SettingsStorage.Music.class);
        SettingsStorage.LOGGER = SettingsLoader.loadExternalSettings(SettingsStorage.Logger.class);
    }

    private static void initInternalSettings() throws IOException{
        SettingsStorage.NETWORK = SettingsLoader.loadInternalSettings(SettingsStorage.Network.class);
    }

    private static void saveDefaultSettingsToExternalSettings() throws IOException{
        saveDefaultSettingsToExternalSettings(SettingsStorage.Display.class);
        saveDefaultSettingsToExternalSettings(SettingsStorage.Music.class);
        saveDefaultSettingsToExternalSettings(SettingsStorage.Logger.class);
    }

    private static <T> void saveDefaultSettingsToExternalSettings(Class<T> settingsContainerClass) throws IOException{
        T settingContainerObject = SettingsLoader.loadInternalSettings(settingsContainerClass);
        SettingsLoader.saveExternalSettings(settingContainerObject);
    }
}
