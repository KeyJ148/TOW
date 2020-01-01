package tow.engine.resources.settings;

import tow.engine.resources.JsonContainerLoader;

import java.io.IOException;

public class SettingsLoader {

    private static final String PATH_EXTERNAL = "settings/";
    private static final String PATH_INTERNAL = "res/settings/";

    public static <T> T loadExternalSettings(Class<T> settingsContainerClass) throws IOException {
        String path = PATH_EXTERNAL + getSettingsFileName(settingsContainerClass);
        return JsonContainerLoader.loadExternalFile(settingsContainerClass, path);
    }

    public static <T> void saveExternalSettings(T settingsContainerObject) throws IOException {
        String path = PATH_EXTERNAL + getSettingsFileName(settingsContainerObject.getClass());
        JsonContainerLoader.saveExternalFile(settingsContainerObject, path);
    }

    public static <T> T loadInternalSettings(Class<T> settingsContainerClass) throws IOException {
        String path = PATH_INTERNAL + getSettingsFileName(settingsContainerClass);
        return JsonContainerLoader.loadInternalFile(settingsContainerClass, path);
    }

    private static String getSettingsFileName(Class settingsClass){
        return settingsClass.getSimpleName().toLowerCase() + ".json";
    }
}
